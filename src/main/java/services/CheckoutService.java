package services;

import common.calendar.BusinessCalendar;
import common.constants.StringFormats;
import common.exceptions.businessdata.RentalOfferNotFoundException;
import common.exceptions.businessdata.ToolNotFoundException;
import common.exceptions.businesslogic.InvalidDiscountException;
import common.exceptions.businesslogic.InvalidRentalDayCountException;
import common.exceptions.businessdata.RentalPeriodConflictException;
import dto.RentalAgreement;
import dto.RentalOffer;
import dto.Tool;
import org.apache.commons.lang3.time.DateUtils;
import services.factory.RentalAgreementServiceFactory;
import services.factory.RentalOfferingsServiceFactory;
import services.factory.ToolsServiceFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CheckoutService {

    public static final int MIN_RENTAL_DAY_COUNT = 1;
    public static final int MIN_DISCOUNT_PCT = 0;
    public static final int MAX_DISCOUNT_PCT = 100;

    public RentalAgreement checkout(String toolCode, Date checkout, int rentalDayCount, int discountPct) {
        checkRentalDayCountValid(rentalDayCount);
        checkDiscountPctValid(discountPct);
        checkToolAvailability(toolCode, checkout, rentalDayCount);

        ToolsService toolsService = ToolsServiceFactory.getToolsService();
        Tool tool = toolsService.fetchTool(toolCode);
        if(tool == null) {
            throw new ToolNotFoundException("There is no " + toolCode + " tool code.");
        }
        RentalOfferingsService rentalOfferingsService = RentalOfferingsServiceFactory.getRentalOfferingsService();
        RentalOffer rentalOffer = rentalOfferingsService.fetchRentalOffer(tool.getToolType());
        if(rentalOffer == null) {
            throw new RentalOfferNotFoundException("There is no offering for " + toolCode + ".");
        }

        Date dueDate = calculateDueDate(checkout, rentalDayCount);
        int chargeDays = calculateChargeDays(checkout, rentalDayCount, rentalOffer.isHolidayCharge(), rentalOffer.isWeekdayCharge(), rentalOffer.isWeekendCharge());
        double prediscountCharge = calculatePrediscountCharge(rentalOffer.getDailyCharge(), chargeDays);
        double discountAmt = calculateDiscountAmt(prediscountCharge, discountPct);
        double charge = calculateCharge(prediscountCharge, discountAmt);

        return new RentalAgreement(toolCode, tool.getToolType(), tool.getBrand(), rentalDayCount, checkout, dueDate,
                rentalOffer.getDailyCharge(), chargeDays, prediscountCharge, discountPct, discountAmt, charge);
    }

    public static Date calculateDueDate(Date checkoutDate, int rentalDays) {
        Calendar dueDateCal = BusinessCalendar.getCalendar(checkoutDate);
        dueDateCal.add(Calendar.DATE, rentalDays);
        dueDateCal.set(Calendar.HOUR_OF_DAY, 23);
        dueDateCal.set(Calendar.MINUTE, 59);
        dueDateCal.set(Calendar.SECOND, 59);
        return dueDateCal.getTime();
    }

    public static int calculateChargeDays(Date checkout, int rentalDays, boolean isHolidayCharge, boolean isWeekDayCharge, boolean isWeekendCharge) {
        Calendar currentDayOfRental = BusinessCalendar.getCalendar(checkout);
        currentDayOfRental.add(Calendar.DATE, 1);

        int chargeDays = 0;
        for(int i = 0; i < rentalDays; i++) {
            if(BusinessCalendar.isHoliday(currentDayOfRental)) {
                if(isHolidayCharge) {
                    chargeDays++;
                }
            } else {
                if (isWeekDayCharge && BusinessCalendar.isWeekday(currentDayOfRental)) {
                    chargeDays++;
                } else if(isWeekendCharge && BusinessCalendar.isWeekend(currentDayOfRental)) {
                    chargeDays++;
                }
            }
            currentDayOfRental.add(Calendar.DATE, 1);
        }

        return chargeDays;
    }

    public static double calculatePrediscountCharge(double dailyCharge, int chargeDays) {
        double prediscountCharge = dailyCharge * chargeDays;
        return Math.round(prediscountCharge * 100) / 100.00;

    }

    public static double calculateDiscountAmt(double prediscountCharge, double discountPct) {
        double discountAmt = prediscountCharge * discountPct / 100;
        return Math.round(discountAmt * 100) / 100.00;
    }

    public static double calculateCharge(double prediscountCharge, double discountAmt) {
        double charge = prediscountCharge - discountAmt;
        return Math.round(charge * 100) / 100.00;
    }

    private void checkRentalDayCountValid(int rentalDayCount) throws InvalidRentalDayCountException {
        if(rentalDayCount < MIN_RENTAL_DAY_COUNT) {
            throw new InvalidRentalDayCountException("The day count needs to be of at least " + MIN_RENTAL_DAY_COUNT + " day.");
        }
    }

    private void checkDiscountPctValid(int discountPct) throws InvalidDiscountException {
        if(discountPct < MIN_DISCOUNT_PCT) {
            throw new InvalidDiscountException("The minimum assignable discount possible is " + StringFormats.numberToPercentString(MIN_DISCOUNT_PCT) + ".");
        } else if(MAX_DISCOUNT_PCT < discountPct) {
            throw new InvalidDiscountException("The maximum assignable discount possible is " + StringFormats.numberToPercentString(MAX_DISCOUNT_PCT) + ".");
        }
    }

    private void checkToolAvailability(String toolCode, Date checkout, int rentalDayCount) throws RentalPeriodConflictException {
        RentalAgreementService rentalAgreementService = RentalAgreementServiceFactory.getRentalAgreementService();
        List<RentalAgreement> existingConflictingRentalAgreements = rentalAgreementService.fetchEffectiveRentalAgreementsForToolDuringPeriod(toolCode, checkout, DateUtils.addDays(checkout, rentalDayCount));

        if(existingConflictingRentalAgreements.size() > 0) {
           throw new RentalPeriodConflictException("This tool has already been checked out and is unavailable for this rental period.");
        }
    }
}

package services;

import common.calendar.BusinessCalendar;
import common.constants.StringFormats;
import common.exceptions.businessdata.RentalPeriodConflictException;
import common.exceptions.businesslogic.InvalidDiscountException;
import common.exceptions.businesslogic.InvalidRentalDayCountException;
import dto.RentalAgreement;
import org.junit.Assert;
import org.junit.Test;
import services.factory.RentalAgreementServiceFactory;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.fail;

public class CheckoutServiceTest {

    @Test()
    public void checkoutTestMinRentalTimeErrorThrown() throws ParseException {
        CheckoutService service = new CheckoutService();

        String toolCode = "JAKR";
        Date checkoutDate = BusinessCalendar.parse("09/03/15", StringFormats.MM_dd_yy);
        int rentalDays = 0;
        int discount = 20;

        try {
            RentalAgreement rentalAgreement = service.checkout(toolCode, checkoutDate, rentalDays, discount);
            fail("The method call \"service.checkout(toolCode, checkoutDate, rentalDays, discount)\" should have thrown an error.");
        } catch (InvalidRentalDayCountException e) {
            String expectedMessage = "The day count needs to be of at least " + CheckoutService.MIN_RENTAL_DAY_COUNT + " day.";
            Assert.assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test()
    public void checkoutTestMaxDiscountPctErrorThrown() throws ParseException {
        CheckoutService service = new CheckoutService();

        String toolCode = "JAKR";
        Date checkoutDate = BusinessCalendar.parse("09/03/15", StringFormats.MM_dd_yy);
        int rentalDays = 5;
        int discount = 101;

        try {
            RentalAgreement rentalAgreement = service.checkout(toolCode, checkoutDate, rentalDays, discount);
            fail("The method call \"service.checkout(toolCode, checkoutDate, rentalDays, discount)\" should have thrown an error.");
        } catch (InvalidDiscountException e) {
            String expectedMessage = "The maximum assignable discount possible is " + StringFormats.numberToPercentString(CheckoutService.MAX_DISCOUNT_PCT) + ".";
            Assert.assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test()
    public void checkoutTestMinDiscountPctErrorThrown() throws ParseException {
        CheckoutService service = new CheckoutService();

        String toolCode = "JAKR";
        Date checkoutDate = BusinessCalendar.parse("09/03/15", StringFormats.MM_dd_yy);
        int rentalDays = 5;
        int discount = -1;

        try {
            RentalAgreement rentalAgreement = service.checkout(toolCode, checkoutDate, rentalDays, discount);
            fail("The method call \"service.checkout(toolCode, checkoutDate, rentalDays, discount)\" should have thrown an error.");
        } catch (InvalidDiscountException e) {
            String expectedMessage = "The minimum assignable discount possible is " + StringFormats.numberToPercentString(CheckoutService.MIN_DISCOUNT_PCT) + ".";
            Assert.assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test()
    public void checkoutTestConflictingRentalAgreementErrorThrown() throws ParseException {
        RentalAgreementService rentalAgreementService = RentalAgreementServiceFactory.getRentalAgreementService();

        // Clear all existing rental agreements.
        rentalAgreementService.deleteAllRentalAgreements();
        List<RentalAgreement> rentalAgreements = rentalAgreementService.fetchAllRentalAgreements();
        Assert.assertEquals(0, rentalAgreements.size());

        // Adds test rental agreement.
        Date checkOutDate1 = BusinessCalendar.parse("10/12/2021", StringFormats.MM_dd_yyyy);
        Date dueDate1 = BusinessCalendar.parse("10/17/2021", StringFormats.MM_dd_yyyy);
        RentalAgreement rentalAgreement = new RentalAgreement("LADW", "Ladder", "Werner", 5, checkOutDate1,
                dueDate1, 1.99, 5, 5.90, 100, 5.90, 0);
        rentalAgreementService.addNewRentalAgreement(rentalAgreement);
        rentalAgreements = rentalAgreementService.fetchAllRentalAgreements();
        Assert.assertEquals(1, rentalAgreements.size());


        CheckoutService checkoutService = new CheckoutService();

        // This checkout is valid because this is a different tool.
        String toolCode = "CHNS";
        Date checkoutDate2 = BusinessCalendar.parse("10/15/2021", StringFormats.MM_dd_yyyy);
        int rentalDays = 5;
        int discount = 10;
        RentalAgreement rentalAgreement1 = checkoutService.checkout(toolCode, checkoutDate2, rentalDays, discount);
        rentalAgreementService.addNewRentalAgreement(rentalAgreement1);
        rentalAgreements = rentalAgreementService.fetchAllRentalAgreements();
        Assert.assertEquals(2, rentalAgreements.size());

        // This checkout should throw a conflicting rental period exception because the tool is already checked out for this period.
        toolCode = "LADW";
        try {
            checkoutService.checkout(toolCode, checkoutDate2, rentalDays, discount);
            fail("The method call \"checkoutService.checkout(toolCode, checkoutDate, rentalDays, discount)\" should have thrown an error.");
        } catch (RentalPeriodConflictException e) {
            String expectedMessage = "This tool has already been checked out and is unavailable for this rental period.";
            Assert.assertEquals(expectedMessage, e.getMessage());

            // Clear the data.
            rentalAgreementService.deleteAllRentalAgreements();
        }
    }
}

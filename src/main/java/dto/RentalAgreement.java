package dto;

import common.calendar.BusinessCalendar;
import common.constants.StringFormats;

import java.util.Date;

public class RentalAgreement {
    private String toolCode;
    private String tooltype;
    private String brand;
    private int rentalDays;
    private Date checkoutDate;
    private Date dueDate;
    private double dailyRentalCharge;
    private int chargeDays;
    private double prediscountCharge;
    private int discountPct;
    private double discountAmt;
    private double charge;

    public RentalAgreement(String toolCode, String tooltype, String brand, int rentalDays, Date checkoutDate, Date dueDate,
                           double dailyRentalCharge, int chargeDays, double prediscountCharge, int discountPct, double discountAmt, double charge) {
        this.toolCode = toolCode;
        this.tooltype = tooltype;
        this.brand = brand;
        this.rentalDays = rentalDays;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.dailyRentalCharge = dailyRentalCharge;
        this.chargeDays = chargeDays;
        this.prediscountCharge = prediscountCharge;
        this.discountPct = discountPct;
        this.discountAmt = discountAmt;
        this.charge = charge;
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public String getTooltype() {
        return tooltype;
    }

    public void setTooltype(String tooltype) {
        this.tooltype = tooltype;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public double getDailyRentalCharge() {
        return dailyRentalCharge;
    }

    public void setDailyRentalCharge(double dailyRentalCharge) {
        this.dailyRentalCharge = dailyRentalCharge;
    }

    public int getChargeDays() {
        return chargeDays;
    }

    public void setChargeDays(int chargeDays) {
        this.chargeDays = chargeDays;
    }

    public double getPrediscountCharge() {
        return prediscountCharge;
    }

    public void setPrediscountCharge(double prediscountCharge) {
        this.prediscountCharge = prediscountCharge;
    }

    public int getDiscountPct() {
        return discountPct;
    }

    public void setDiscountPct(int discountPct) {
        this.discountPct = discountPct;
    }

    public double getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(double discountAmt) {
        this.discountAmt = discountAmt;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public String toString() {
        String toString = "Tool Code: " + toolCode;
        toString += ", Tool Type: " + tooltype;
        toString += ", Brand: " + brand;
        toString += ", Rental Days: " + rentalDays;
        toString += ", Checkout Date: " + StringFormats.dateToString(checkoutDate, StringFormats.MM_dd_yy);
        toString += ", Due Date: " + StringFormats.dateToString(dueDate, StringFormats.MM_dd_yy);
        toString += ", Daily Rental Charge: " + StringFormats.numberToCurrencyString(dailyRentalCharge, StringFormats.ENGLISH_CURRENCY);
        toString += ", Charge Days: " + chargeDays;
        toString += ", Prediscount Charge: " + StringFormats.numberToCurrencyString(prediscountCharge, StringFormats.ENGLISH_CURRENCY);
        toString += ", Discount: " + StringFormats.numberToPercentString(discountPct);
        toString += ", Discount Amount: " + StringFormats.numberToCurrencyString(discountAmt, StringFormats.ENGLISH_CURRENCY);
        toString += ", Final Charge: " + StringFormats.numberToCurrencyString(charge, StringFormats.ENGLISH_CURRENCY);

        return toString;
    }

    public boolean equals(RentalAgreement rentalAgreement) {
        try {
            return toolCode.equals(rentalAgreement.getToolCode()) &&
                    tooltype.equals(rentalAgreement.getTooltype()) &&
                    brand.equals(rentalAgreement.getBrand()) &&
                    rentalDays == rentalAgreement.getRentalDays() &&
                    BusinessCalendar.compare(checkoutDate, rentalAgreement.getCheckoutDate()) == 0 &&
                    BusinessCalendar.compare(dueDate, rentalAgreement.getDueDate()) == 0 &&
                    dailyRentalCharge == rentalAgreement.getDailyRentalCharge() &&
                    chargeDays == rentalAgreement.getChargeDays() &&
                    prediscountCharge == rentalAgreement.getPrediscountCharge() &&
                    discountAmt == rentalAgreement.getDiscountAmt() &&
                    charge == rentalAgreement.getCharge();
        } catch (NullPointerException e) {
            return false;
        }
    }
}

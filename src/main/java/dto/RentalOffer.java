package dto;

import common.constants.StringFormats;

public class RentalOffer {

    private String tooltype;
    private double dailyCharge;
    private boolean weekdayCharge;
    private boolean weekendCharge;
    private boolean holidayCharge;

    public RentalOffer(String tooltype, double dailyCharge, boolean weekdayCharge, boolean weekendCharge, boolean holidayCharge) {
        this.tooltype = tooltype;
        this.dailyCharge = dailyCharge;
        this.weekdayCharge = weekdayCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;
    }

    public String getTooltype() {
        return tooltype;
    }

    public void setTooltype(String tooltype) {
        this.tooltype = tooltype;
    }

    public double getDailyCharge() {
        return dailyCharge;
    }

    public void setDailyCharge(double dailyCharge) {
        this.dailyCharge = dailyCharge;
    }

    public boolean isWeekdayCharge() {
        return weekdayCharge;
    }

    public void setWeekdayCharge(boolean weekdayCharge) {
        this.weekdayCharge = weekdayCharge;
    }

    public boolean isWeekendCharge() {
        return weekendCharge;
    }

    public void setWeekendCharge(boolean weekendCharge) {
        this.weekendCharge = weekendCharge;
    }

    public boolean isHolidayCharge() {
        return holidayCharge;
    }

    public void setHolidayCharge(boolean holidayCharge) {
        this.holidayCharge = holidayCharge;
    }

    public String toString() {
        String toString = "Tool Type: " + tooltype;
        toString += ", Daily Charge: " + StringFormats.numberToCurrencyString(dailyCharge, StringFormats.ENGLISH_CURRENCY);
        toString += ", Weekday Charge: " + weekdayCharge;
        toString += ", Weekend Charge: " + weekendCharge;
        toString += ", Holiday Charge: " + holidayCharge;

        return toString;
    }

    public boolean equals(RentalOffer rentalOffer) {
        try {
            return tooltype.equals(rentalOffer.getTooltype()) &&
                    dailyCharge == rentalOffer.getDailyCharge() &&
                    weekdayCharge == rentalOffer.isWeekdayCharge() &&
                    weekendCharge == rentalOffer.isWeekendCharge() &&
                    holidayCharge == rentalOffer.isHolidayCharge();
        } catch (NullPointerException e) {
            return false;
        }
    }
}

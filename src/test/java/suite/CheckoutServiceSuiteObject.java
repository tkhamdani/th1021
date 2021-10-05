package suite;

import dto.RentalAgreement;

import java.util.Date;

public class CheckoutServiceSuiteObject {
    private int testNumber;
    private String toolCode;
    private Date checkoutDate;
    private int rentalDays;
    private int discount;
    private RentalAgreement rentalAgreement;

    public CheckoutServiceSuiteObject(int testNumber, String toolCode, Date checkoutDate, int rentalDays, int discount, RentalAgreement rentalAgreement) {
        this.testNumber = testNumber;
        this.toolCode = toolCode;
        this.checkoutDate = checkoutDate;
        this.rentalDays = rentalDays;
        this.discount = discount;
        this.rentalAgreement = rentalAgreement;
    }

    public int getTestNumber() {
        return testNumber;
    }

    public void setTestNumber(int testNumber) {
        this.testNumber = testNumber;
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public RentalAgreement getRentalAgreement() {
        return rentalAgreement;
    }

    public void setRentalAgreement(RentalAgreement rentalAgreement) {
        this.rentalAgreement = rentalAgreement;
    }
}

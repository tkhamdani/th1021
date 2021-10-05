package suite;

import common.calendar.BusinessCalendar;
import common.properties.AppPropertiesReader;
import dto.RentalAgreement;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import services.CheckoutService;
import services.RentalAgreementService;
import services.factory.RentalAgreementServiceFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CheckoutTestSuite {

    private final static int CHECKOUT_TEST_NUMBER_COL = 0;
    private final static int CHECKOUT_TOOL_CODE_COL = 1;
    private final static int CHECKOUT_CHECKOUT_DATE_COL = 2;
    private final static int CHECKOUT_RENTAL_DAYS_COL = 3;
    private final static int CHECKOUT_DISCOUNT_COL = 4;
    private final static int RENTAL_AGREEMENT_TOOL_CODE_COL = 5;
    private final static int RENTAL_AGREEMENT_TOOL_TYPE_COL = 6;
    private final static int RENTAL_AGREEMENT_TOOL_BRAND_COL = 7;
    private final static int RENTAL_AGREEMENT_RENTAL_DAYS_COL = 8;
    private final static int RENTAL_AGREEMENT_CHECKOUT_DATE_COL = 9;
    private final static int RENTAL_AGREEMENT_DUE_DATE_COL = 10;
    private final static int RENTAL_AGREEMENT_DAILY_RENTAL_CHARGE_COL = 11;
    private final static int RENTAL_AGREEMENT_CHARGE_DAYS_COL = 12;
    private final static int RENTAL_AGREEMENT_PRE_DISCOUNT_CHARGE_COL = 13;
    private final static int RENTAL_AGREEMENT_DISCOUNT_PERCENT_COL = 14;
    private final static int RENTAL_AGREEMENT_DISCOUNT_AMOUNT_COL = 15;
    private final static int RENTAL_AGREEMENT_FINAL_CHARGE_COL = 16;

    private static final String CHECKOUT_SERVICE_SUITE_SHEET_NAME = "Checkout Service Suite";
    List<CheckoutServiceSuiteObject> checkoutServiceSuiteObjects = new ArrayList<>();

    @Before
    public void getTestData() {
        try {
            String testSuiteFilePath = AppPropertiesReader.get("test.suite.excel", "application.properties");
            if(testSuiteFilePath == null) { throw new FileNotFoundException(); }

            File theFile = new File(testSuiteFilePath);
            InputStream excel = new FileInputStream(theFile);
            XSSFWorkbook workbook = new XSSFWorkbook(excel);

            Sheet sheet = workbook.getSheetAt(workbook.getSheetIndex(CHECKOUT_SERVICE_SUITE_SHEET_NAME));
            int lastIndex = sheet.getLastRowNum();

            for (int i = 2; i < lastIndex; i++) {
                Row data = sheet.getRow(i);
                checkoutServiceSuiteObjects.add(getCheckoutServiceSuiteObjectFromRow(data));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void startCheckoutServiceSuite() {
        CheckoutService checkoutService = new CheckoutService();
        RentalAgreementService rentalAgreementService = RentalAgreementServiceFactory.getRentalAgreementService();

        for(CheckoutServiceSuiteObject suiteObject : checkoutServiceSuiteObjects) {
            rentalAgreementService.deleteAllRentalAgreements();
            RentalAgreement rentalAgreement = checkoutService.checkout(suiteObject.getToolCode(), suiteObject.getCheckoutDate(), suiteObject.getRentalDays(), suiteObject.getDiscount());
            Assert.assertTrue(rentalAgreement.equals(suiteObject.getRentalAgreement()));
        }
    }

    private CheckoutServiceSuiteObject getCheckoutServiceSuiteObjectFromRow(Row data) {
        int checkoutInputTestNumber = Double.valueOf(data.getCell(CHECKOUT_TEST_NUMBER_COL).getNumericCellValue()).intValue();
        String checkoutInputToolCode = data.getCell(CHECKOUT_TOOL_CODE_COL).getStringCellValue();
        Date checkoutInputCheckoutDate = data.getCell(CHECKOUT_CHECKOUT_DATE_COL).getDateCellValue();
        int checkoutInputRentalDays = Double.valueOf(data.getCell(CHECKOUT_RENTAL_DAYS_COL).getNumericCellValue()).intValue();
        int checkoutInputDiscountPct = Double.valueOf(data.getCell(CHECKOUT_DISCOUNT_COL).getNumericCellValue()).intValue();
        String rentalAgreementToolCode = data.getCell(RENTAL_AGREEMENT_TOOL_CODE_COL).getStringCellValue();
        String rentalAgreementoolType = data.getCell(RENTAL_AGREEMENT_TOOL_TYPE_COL).getStringCellValue();
        String rentalAgreementBrand = data.getCell(RENTAL_AGREEMENT_TOOL_BRAND_COL).getStringCellValue();
        int rentalAgreementRentalDays = Double.valueOf(data.getCell(RENTAL_AGREEMENT_RENTAL_DAYS_COL).getNumericCellValue()).intValue();
        Date rentalAgreementCheckoutDate = data.getCell(RENTAL_AGREEMENT_CHECKOUT_DATE_COL).getDateCellValue();
        Date rentalAgreementDueDate = BusinessCalendar.getTimeToBeforeMidnight(data.getCell(RENTAL_AGREEMENT_DUE_DATE_COL).getDateCellValue());
        double rentalAgreementDailyRentalCharge = data.getCell(RENTAL_AGREEMENT_DAILY_RENTAL_CHARGE_COL).getNumericCellValue();
        int rentalAgreementChargeDays = Double.valueOf(data.getCell(RENTAL_AGREEMENT_CHARGE_DAYS_COL).getNumericCellValue()).intValue();
        double rentalAgreementPrediscountCharge = data.getCell(RENTAL_AGREEMENT_PRE_DISCOUNT_CHARGE_COL).getNumericCellValue();
        int rentalAgreementDiscountPct = Double.valueOf(data.getCell(RENTAL_AGREEMENT_DISCOUNT_PERCENT_COL).getNumericCellValue() * 100).intValue();
        double rentalAgreementDiscountAmt = data.getCell(RENTAL_AGREEMENT_DISCOUNT_AMOUNT_COL).getNumericCellValue();
        double rentalAgreementCharge = data.getCell(RENTAL_AGREEMENT_FINAL_CHARGE_COL).getNumericCellValue();

        RentalAgreement rentalAgreement = new RentalAgreement(rentalAgreementToolCode, rentalAgreementoolType, rentalAgreementBrand, rentalAgreementRentalDays, rentalAgreementCheckoutDate,
                rentalAgreementDueDate, rentalAgreementDailyRentalCharge, rentalAgreementChargeDays, rentalAgreementPrediscountCharge, rentalAgreementDiscountPct,
                rentalAgreementDiscountAmt, rentalAgreementCharge);

        return new CheckoutServiceSuiteObject(checkoutInputTestNumber, checkoutInputToolCode, checkoutInputCheckoutDate, checkoutInputRentalDays, checkoutInputDiscountPct, rentalAgreement);
    }
}

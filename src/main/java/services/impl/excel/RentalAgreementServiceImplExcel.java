package services.impl.excel;

import common.calendar.BusinessCalendar;
import common.constants.StringFormats;
import common.properties.AppPropertiesReader;
import dto.RentalAgreement;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import services.RentalAgreementService;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RentalAgreementServiceImplExcel implements RentalAgreementService {

    private final static String RENTAL_AGREEMENT_SHEET_NAME = "Rental Agreement";
    private final static int TOOL_CODE_COL = 0;
    private final static int TOOL_TYPE_COL = 1;
    private final static int TOOL_BRAND_COL = 2;
    private final static int RENTAL_DAYS_COL = 3;
    private final static int CHECKOUT_DATE_COL = 4;
    private final static int DUE_DATE_COL = 5;
    private final static int DAILY_RENTAL_CHARGE_COL = 6;
    private final static int CHARGE_DAYS_COL = 7;
    private final static int PRE_DISCOUNT_CHARGE_COL = 8;
    private final static int DISCOUNT_PERCENT_COL = 9;
    private final static int DISCOUNT_AMOUNT_COL = 10;
    private final static int FINAL_CHARGE_COL = 11;

    public List<RentalAgreement> fetchAllRentalAgreements() {
        List<RentalAgreement> rentalAgreements = new ArrayList<>();
        try {
            String filePath = AppPropertiesReader.get("data.excel", "application.properties");
            if(filePath == null) { throw new FileNotFoundException(); }

            File theFile = new File(filePath);
            InputStream excel = new FileInputStream(theFile);
            XSSFWorkbook workbook = new XSSFWorkbook(excel);

            Sheet sheet = workbook.getSheetAt(workbook.getSheetIndex(RENTAL_AGREEMENT_SHEET_NAME));
            int lastIndex = sheet.getLastRowNum();

            for (int i = 1; i <= lastIndex; i++) {
                Row data = sheet.getRow(i);
                rentalAgreements.add(getRentalAgreementFromRow(data));
            }

            return rentalAgreements;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rentalAgreements;
    }

    public List<RentalAgreement> fetchEffectiveRentalAgreementsForToolDuringPeriod(String toolCode, Date startDate, Date endDate) {
        List<RentalAgreement> rentalAgreements = new ArrayList<>();
        try {
            String filePath = AppPropertiesReader.get("data.excel", "application.properties");
            if(filePath == null) { throw new FileNotFoundException(); }

            File theFile = new File(filePath);
            InputStream excel = new FileInputStream(theFile);
            XSSFWorkbook workbook = new XSSFWorkbook(excel);

            Sheet sheet = workbook.getSheetAt(workbook.getSheetIndex(RENTAL_AGREEMENT_SHEET_NAME));
            int lastIndex = sheet.getLastRowNum();

            for (int i = 1; i <= lastIndex; i++) {
                Row data = sheet.getRow(i);

                String currentToolCode = data.getCell(TOOL_CODE_COL).getStringCellValue();
                Date checkoutDate = data.getCell(CHECKOUT_DATE_COL).getDateCellValue();
                Date dueDate = BusinessCalendar.getTimeToBeforeMidnight(data.getCell(DUE_DATE_COL).getDateCellValue());

                if(currentToolCode.equalsIgnoreCase(toolCode) && BusinessCalendar.isWithinRange(checkoutDate, dueDate, startDate, endDate)) {
                    rentalAgreements.add(getRentalAgreementFromRow(data));
                }
            }

            return rentalAgreements;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rentalAgreements;
    }

    public void addNewRentalAgreement(RentalAgreement rentalAgreement) {
        try {
            String filePath = AppPropertiesReader.get("data.excel", "application.properties");
            if(filePath == null) { throw new FileNotFoundException(); }

            File theFile = new File(filePath);
            InputStream excel = new FileInputStream(theFile);
            XSSFWorkbook workbook = new XSSFWorkbook(excel);

            XSSFSheet sheet = workbook.getSheetAt(workbook.getSheetIndex(RENTAL_AGREEMENT_SHEET_NAME));
            XSSFRow row;

            int lastIndex = sheet.getLastRowNum();
            row = sheet.createRow(++lastIndex);

            CellStyle dateCellStyle = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat(StringFormats.MM_dd_yy));

            Cell cell = row.createCell(TOOL_CODE_COL);
            cell.setCellValue(rentalAgreement.getToolCode());
            cell = row.createCell(TOOL_TYPE_COL);
            cell.setCellValue(rentalAgreement.getTooltype());
            cell = row.createCell(TOOL_BRAND_COL);
            cell.setCellValue(rentalAgreement.getBrand());
            cell = row.createCell(RENTAL_DAYS_COL);
            cell.setCellValue(rentalAgreement.getRentalDays());
            cell = row.createCell(CHECKOUT_DATE_COL);
            cell.setCellStyle(dateCellStyle);
            cell.setCellValue(rentalAgreement.getCheckoutDate());
            cell = row.createCell(DUE_DATE_COL);
            cell.setCellStyle(dateCellStyle);
            cell.setCellValue(rentalAgreement.getDueDate());
            cell = row.createCell(DAILY_RENTAL_CHARGE_COL);
            cell.setCellValue(StringFormats.numberToCurrencyString(rentalAgreement.getDailyRentalCharge(), StringFormats.ENGLISH_CURRENCY));
            cell = row.createCell(CHARGE_DAYS_COL);
            cell.setCellValue(rentalAgreement.getChargeDays());
            cell = row.createCell(PRE_DISCOUNT_CHARGE_COL);
            cell.setCellValue(StringFormats.numberToCurrencyString(rentalAgreement.getPrediscountCharge(), StringFormats.ENGLISH_CURRENCY));
            cell = row.createCell(DISCOUNT_PERCENT_COL);
            cell.setCellValue(rentalAgreement.getDiscountPct());
            cell = row.createCell(DISCOUNT_AMOUNT_COL);
            cell.setCellValue(rentalAgreement.getDiscountAmt());
            cell = row.createCell(FINAL_CHARGE_COL);
            cell.setCellValue(StringFormats.numberToCurrencyString(rentalAgreement.getCharge(), StringFormats.ENGLISH_CURRENCY));

            FileOutputStream out = new FileOutputStream(theFile);
            workbook.write(out);
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllRentalAgreements() {
        try {
            String filePath = AppPropertiesReader.get("data.excel", "application.properties");
            if(filePath == null) { throw new FileNotFoundException(); }

            File theFile = new File(filePath);
            InputStream excel = new FileInputStream(theFile);
            XSSFWorkbook workbook = new XSSFWorkbook(excel);

            Sheet sheet = workbook.getSheetAt(workbook.getSheetIndex(RENTAL_AGREEMENT_SHEET_NAME));
            int lastIndex = sheet.getLastRowNum();

            for (int i = lastIndex ; i > 0; i--) {
                Row data = sheet.getRow(i);
                sheet.removeRow(data);
            }

            FileOutputStream out = new FileOutputStream(theFile);
            workbook.write(out);
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private RentalAgreement getRentalAgreementFromRow(Row data) {
        String toolCode = data.getCell(TOOL_CODE_COL).getStringCellValue();
        String toolType = data.getCell(TOOL_TYPE_COL).getStringCellValue();
        String brand = data.getCell(TOOL_BRAND_COL).getStringCellValue();
        int rentalDays = Double.valueOf(data.getCell(RENTAL_DAYS_COL).getNumericCellValue()).intValue();
        Date checkoutDate = data.getCell(CHECKOUT_DATE_COL).getDateCellValue();
        Date dueDate = BusinessCalendar.getTimeToBeforeMidnight(data.getCell(DUE_DATE_COL).getDateCellValue());
        double dailyRentalCharge = StringFormats.currencyToNumber(data.getCell(DAILY_RENTAL_CHARGE_COL).getStringCellValue());
        int chargeDays = Double.valueOf(data.getCell(CHARGE_DAYS_COL).getNumericCellValue()).intValue();
        double prediscountCharge = StringFormats.currencyToNumber(data.getCell(PRE_DISCOUNT_CHARGE_COL).getStringCellValue());
        int discountPct = Double.valueOf(data.getCell(DISCOUNT_PERCENT_COL).getNumericCellValue()).intValue();
        int discountAmt = Double.valueOf(data.getCell(DISCOUNT_AMOUNT_COL).getNumericCellValue()).intValue();
        double charge = StringFormats.currencyToNumber(data.getCell(FINAL_CHARGE_COL).getStringCellValue());

        return new RentalAgreement(toolCode, toolType, brand, rentalDays, checkoutDate, dueDate, dailyRentalCharge,
                    chargeDays, prediscountCharge, discountPct, discountAmt, charge);

    }
}

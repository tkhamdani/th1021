package services.impl.excel;

import common.properties.AppPropertiesReader;
import dto.RentalOffer;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import services.RentalOfferingsService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RentalOfferingsServiceImplExcel implements RentalOfferingsService {

    private final static String RENTAL_OFFERINGS_SHEET_NAME = "Rental Offerings";
    private final static int TOOL_TYPE_COL = 0;
    private final static int DAILY_CHARGE_COL = 1;
    private final static int WEEKDAY_CHARGE_COL = 2;
    private final static int WEEKEND_CHARGE_COL = 3;
    private final static int HOLIDAY_CHARGE_COL = 4;

    public RentalOffer fetchRentalOffer(String tooltype) {
        try {
            String filePath = AppPropertiesReader.get("data.excel", "application.properties");
            if(filePath == null) { throw new FileNotFoundException(); }

            File theFile = new File(filePath);
            InputStream excel = new FileInputStream(theFile);
            XSSFWorkbook workbook = new XSSFWorkbook(excel);

            Sheet sheet = workbook.getSheetAt(workbook.getSheetIndex(RENTAL_OFFERINGS_SHEET_NAME));
            int lastIndex = sheet.getLastRowNum();

            for (int i = 1; i <= lastIndex; i++) {
                Row data = sheet.getRow(i);
                String currentToolType = data.getCell(TOOL_TYPE_COL).getStringCellValue();
                if(currentToolType.equalsIgnoreCase(tooltype)) {
                    return getRentalOfferFromRow(data);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<RentalOffer> fetchAllRentalOffers() {

        try {
            String filePath = AppPropertiesReader.get("data.excel", "application.properties");
            if(filePath == null) { throw new FileNotFoundException(); }

            File theFile = new File(filePath);
            InputStream excel = new FileInputStream(theFile);
            XSSFWorkbook workbook = new XSSFWorkbook(excel);

            Sheet sheet = workbook.getSheetAt(workbook.getSheetIndex(RENTAL_OFFERINGS_SHEET_NAME));
            int lastIndex = sheet.getLastRowNum();

            List<RentalOffer> rentalOffers = new ArrayList<>();
            for (int i = 1; i <= lastIndex; i++) {
                Row data = sheet.getRow(i);
                rentalOffers.add(getRentalOfferFromRow(data));
            }

            return rentalOffers;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private RentalOffer getRentalOfferFromRow(Row data) {
        String currentToolType = data.getCell(TOOL_TYPE_COL).getStringCellValue();
        double dailyCharge = data.getCell(DAILY_CHARGE_COL).getNumericCellValue();
        boolean weekdayCharge = convertToBoolean(data.getCell(WEEKDAY_CHARGE_COL).getStringCellValue());
        boolean weekendCharge = convertToBoolean(data.getCell(WEEKEND_CHARGE_COL).getStringCellValue());
        boolean holidayCharge = convertToBoolean(data.getCell(HOLIDAY_CHARGE_COL).getStringCellValue());
        return new RentalOffer(currentToolType, dailyCharge, weekdayCharge, weekendCharge, holidayCharge);
    }

    private boolean convertToBoolean(String yesNo) {
        if("yes".equalsIgnoreCase(yesNo)) return true;
        else return false;
    }
}

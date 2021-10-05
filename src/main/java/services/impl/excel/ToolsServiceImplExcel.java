package services.impl.excel;

import common.properties.AppPropertiesReader;
import dto.Tool;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import services.ToolsService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ToolsServiceImplExcel implements ToolsService {

    private final static String TOOLS_SHEET_NAME = "Tools";
    private final static int TOOL_TYPE_COL = 0;
    private final static int BRAND_COL = 1;
    private final static int TOOL_CODE_COL = 2;

    public Tool fetchTool(String toolCode) {
        try {
            String filePath = AppPropertiesReader.get("data.excel", "application.properties");
            if(filePath == null) { throw new FileNotFoundException(); }

            File theFile = new File(filePath);
            InputStream excel = new FileInputStream(theFile);
            XSSFWorkbook workbook = new XSSFWorkbook(excel);

            Sheet sheet = workbook.getSheetAt(workbook.getSheetIndex(TOOLS_SHEET_NAME));
            int lastIndex = sheet.getLastRowNum();

            for (int i = 1; i <= lastIndex; i++) {
                Row data = sheet.getRow(i);
                String currentToolCode = data.getCell(TOOL_CODE_COL).getStringCellValue();
                if(currentToolCode.equalsIgnoreCase(toolCode)) {
                    return getToolFromRow(data);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Tool> fetchAllTools() {
        try {
            String filePath = AppPropertiesReader.get("data.excel", "application.properties");
            if(filePath == null) { throw new FileNotFoundException(); }

            File theFile = new File(filePath);
            InputStream excel = new FileInputStream(theFile);
            XSSFWorkbook workbook = new XSSFWorkbook(excel);

            Sheet sheet = workbook.getSheetAt(workbook.getSheetIndex(TOOLS_SHEET_NAME));
            int lastIndex = sheet.getLastRowNum();

            List<Tool> tools = new ArrayList<>();
            for (int i = 1; i <= lastIndex; i++) {
                Row data = sheet.getRow(i);
                tools.add(getToolFromRow(data));
            }

            return tools;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Tool getToolFromRow(Row data) {
        String toolType = data.getCell(TOOL_TYPE_COL).getStringCellValue();
        String brand = data.getCell(BRAND_COL).getStringCellValue();
        String toolCode = data.getCell(TOOL_CODE_COL).getStringCellValue();
        return new Tool(toolType, brand, toolCode);
    }
}

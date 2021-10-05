package services.factory;

import common.properties.AppPropertiesReader;
import services.ToolsService;
import services.impl.excel.ToolsServiceImplExcel;

public class ToolsServiceFactory {

    private ToolsServiceFactory() {}

    public static ToolsService getToolsService() {
        String serviceInstanceType = AppPropertiesReader.get("service.type", "application.properties").toLowerCase();
        switch(serviceInstanceType) {
            case "excel":
                return new ToolsServiceImplExcel();
        }

        System.out.println("No known service instance type for runtype: " + serviceInstanceType);
        return null;
    }
}

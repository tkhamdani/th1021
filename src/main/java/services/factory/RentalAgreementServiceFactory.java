package services.factory;

import common.properties.AppPropertiesReader;
import services.RentalAgreementService;
import services.impl.excel.RentalAgreementServiceImplExcel;

public class RentalAgreementServiceFactory {

    private RentalAgreementServiceFactory() {}

    public static RentalAgreementService getRentalAgreementService() {
        String serviceInstanceType = AppPropertiesReader.get("service.type", "application.properties").toLowerCase();
        switch(serviceInstanceType) {
        case "excel":
            return new RentalAgreementServiceImplExcel();
        }

        System.out.println("No known service instance type for runtype: " + serviceInstanceType);
        return null;
    }
}

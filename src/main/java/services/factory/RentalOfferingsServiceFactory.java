package services.factory;

import common.properties.AppPropertiesReader;
import services.RentalOfferingsService;
import services.impl.excel.RentalOfferingsServiceImplExcel;

public class RentalOfferingsServiceFactory {

    private RentalOfferingsServiceFactory() {}

    public static RentalOfferingsService getRentalOfferingsService() {
        String serviceInstanceType = AppPropertiesReader.get("service.type", "application.properties").toLowerCase();
        switch(serviceInstanceType) {
            case "excel":
                return new RentalOfferingsServiceImplExcel();
        }

        System.out.println("No known service instance type for runtype: " + serviceInstanceType);
        return null;
    }
}

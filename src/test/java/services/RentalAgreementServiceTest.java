package services;

import common.calendar.BusinessCalendar;
import common.constants.StringFormats;
import dto.RentalAgreement;
import org.junit.Assert;
import org.junit.Test;
import services.factory.RentalAgreementServiceFactory;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class RentalAgreementServiceTest {

    @Test
    public void rentalAgreementsTest() throws ParseException {
        RentalAgreementService service = RentalAgreementServiceFactory.getRentalAgreementService();

        // Clear all existing rental agreements.
        service.deleteAllRentalAgreements();
        List<RentalAgreement> rentalAgreements = service.fetchAllRentalAgreements();
        Assert.assertEquals(0, rentalAgreements.size());


        // Rental agreements to add.
        Date checkOutDate1 = BusinessCalendar.parse("10/12/2021", StringFormats.MM_dd_yyyy);
        Date dueDate1 = BusinessCalendar.parse("10/17/2021", StringFormats.MM_dd_yyyy);
        RentalAgreement rentalAgreement1 = new RentalAgreement("LADW", "Ladder", "Werner", 5, checkOutDate1,
                dueDate1, 1.99, 5, 5.90, 100, 5.90, 0);

        Date checkOutDate2 = BusinessCalendar.parse("09/12/2020", StringFormats.MM_dd_yyyy);
        Date dueDate2 = BusinessCalendar.parse("09/17/2020", StringFormats.MM_dd_yyyy);
        RentalAgreement rentalAgreement2 = new RentalAgreement("LADW", "Ladder", "Werner", 5, checkOutDate2,
                dueDate2, 1.99, 5, 5.90, 100, 5.90, 0);

        // Add the two new rental agreements.
        service.addNewRentalAgreement(rentalAgreement1);
        service.addNewRentalAgreement(rentalAgreement2);

        rentalAgreements = service.fetchAllRentalAgreements();
        Assert.assertEquals(2, rentalAgreements.size());

        Date effectiveStartDate = BusinessCalendar.parse("10/15/2021", StringFormats.MM_dd_yyyy);
        Date effectiveEndDate = BusinessCalendar.parse("10/22/2021", StringFormats.MM_dd_yyyy);
        List<RentalAgreement> effectiveRentalAgreements = service.fetchEffectiveRentalAgreementsForToolDuringPeriod("LADW", effectiveStartDate, effectiveEndDate);
        Assert.assertEquals(1, effectiveRentalAgreements.size());


        // Clear all existing rental agreements.
        service.deleteAllRentalAgreements();
        rentalAgreements = service.fetchAllRentalAgreements();
        Assert.assertEquals(0, rentalAgreements.size());
    }
}

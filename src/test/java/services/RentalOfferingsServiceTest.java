package services;

import dto.RentalOffer;
import org.junit.Assert;
import org.junit.Test;
import services.factory.RentalOfferingsServiceFactory;

import java.util.List;

public class RentalOfferingsServiceTest {

    @Test
    public void fetchRentalOfferTest() {
        RentalOfferingsService service = RentalOfferingsServiceFactory.getRentalOfferingsService();
        RentalOffer rentalOffer;

        rentalOffer = service.fetchRentalOffer("Ladder");
        Assert.assertEquals("Ladder", rentalOffer.getTooltype());
        Assert.assertEquals( 1.99, rentalOffer.getDailyCharge(),0);
        Assert.assertTrue(rentalOffer.isWeekdayCharge());
        Assert.assertTrue(rentalOffer.isWeekendCharge());
        Assert.assertFalse(rentalOffer.isHolidayCharge());

        rentalOffer = service.fetchRentalOffer("Chainsaw");
        Assert.assertEquals("Chainsaw", rentalOffer.getTooltype());
        Assert.assertEquals(1.49, rentalOffer.getDailyCharge(), 0);
        Assert.assertTrue(rentalOffer.isWeekdayCharge());
        Assert.assertFalse(rentalOffer.isWeekendCharge());
        Assert.assertTrue(rentalOffer.isHolidayCharge());


        rentalOffer = service.fetchRentalOffer("Jackhammer");
        Assert.assertEquals("Jackhammer", rentalOffer.getTooltype());
        Assert.assertEquals(2.99, rentalOffer.getDailyCharge(), 0);
        Assert.assertTrue(rentalOffer.isWeekdayCharge());
        Assert.assertFalse(rentalOffer.isWeekendCharge());
        Assert.assertFalse(rentalOffer.isHolidayCharge());
    }

    @Test
    public void fetchAllRentalOffersTest() {
        RentalOfferingsService service = RentalOfferingsServiceFactory.getRentalOfferingsService();
        List<RentalOffer> rentalOffers = service.fetchAllRentalOffers();
        int expectedNumberOfRentalOffers = 3;

        Assert.assertEquals(expectedNumberOfRentalOffers, rentalOffers.size());
    }

}

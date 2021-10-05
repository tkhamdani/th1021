package services;

import dto.RentalOffer;

import java.util.List;

public interface RentalOfferingsService {

    RentalOffer fetchRentalOffer(String tooltype);
    List<RentalOffer> fetchAllRentalOffers();
}

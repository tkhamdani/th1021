package services;

import dto.RentalAgreement;

import java.util.Date;
import java.util.List;

public interface RentalAgreementService {

    List<RentalAgreement> fetchAllRentalAgreements();
    void addNewRentalAgreement(RentalAgreement rentalAgreement);
    void deleteAllRentalAgreements();
    List<RentalAgreement> fetchEffectiveRentalAgreementsForToolDuringPeriod(String toolCode, Date startDate, Date endDate);
}

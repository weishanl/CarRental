package vehicle.integration;

import vehicle.domain.RentalAgreement;

public interface IRentalAgreementDao {

    void createRentalAgreement(RentalAgreement ra1);

    RentalAgreement getRentalAgreement(Long key);

    void removeRentalAgreement(Long key);

}

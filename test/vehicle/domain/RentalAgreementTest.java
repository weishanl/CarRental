package vehicle.domain;

import static junit.framework.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import vehicle.type.Result;

public class RentalAgreementTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(RentalAgreementTest.class);
    }

    public static RentalAgreement generateRentalAgreement() {
        return new RentalAgreement(generateRenter(), generateVehicle(), generateRatePlan());
    }

    @Test
    public void createAndValidateRentalAgreement() {
        final RentalAgreement ra1 = generateRentalAgreement();
        final Result<RentalAgreement> result = new Result<RentalAgreement>(ra1, true);
        ra1.validate(result);
        assertTrue("Expected success", result.isSuccess());
    }

    private static Vehicle generateVehicle() {
        return VehicleTest.generateVehicle();
    }

    private static RatePlan generateRatePlan() {
        return RatePlanTest.generateRatePlan();
    }

    public static Renter generateRenter() {
        return RenterTest.generateRenter();
    }
}

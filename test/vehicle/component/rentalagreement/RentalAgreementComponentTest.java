package vehicle.component.rentalagreement;

import junit.framework.Assert;
import junit.framework.JUnit4TestAdapter;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import vehicle.component.rateplan.RatePlanComponentTest;
import vehicle.component.vehicle.VehicleComponentTest;
import vehicle.component.vehicletype.VehicleTypeComponentTest;
import vehicle.configuration.CarRentalBeanFactory;
import vehicle.domain.AuthorizedOperator;
import vehicle.domain.AuthorizedOperatorTest;
import vehicle.domain.RentalAgreement;
import vehicle.domain.RentalAgreementTest;
import vehicle.domain.Renter;
import vehicle.domain.Vehicle;
import vehicle.exception.ObjectDoesNotExist;
import vehicle.type.Result;
import vehicle.type.ValidationStatus;

public class RentalAgreementComponentTest extends Assert {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(RentalAgreementComponentTest.class);
    }
    private RentalAgreementComponent component;
    private RentalAgreement ra1;

    private static RentalAgreement generateRentalAgreement() {
        return RentalAgreementTest.generateRentalAgreement();
    }

    private AuthorizedOperator generateAuthorizedOperator() {
        return AuthorizedOperatorTest.generateAuthorizedOperator();
    }

    public static RentalAgreementComponent getRentalAgreementComponent() {
        return CarRentalBeanFactory.getBean(RentalAgreementComponent.class);
    }

    public static RentalAgreement installTestRentalAgreement() {
        VehicleComponentTest.installTestVehicle();
        RatePlanComponentTest.installTestRatePlan();
        final Result<RentalAgreement> result = getRentalAgreementComponent().createRentalAgreement(
                generateRentalAgreement());
        assertTrue(result.isSuccess());
        return result.getValue();
    }

    public static void uninstallTestRentalAgreement(final long id) {
        getRentalAgreementComponent().removeRentalAgreement(id);
        RatePlanComponentTest.uninstallTestRatPlan();
        VehicleComponentTest.uninstallTestVehicle();
    }

    @BeforeClass
    public static void installRequiredObjects() {
        VehicleTypeComponentTest.installTestVehicleType();
        VehicleComponentTest.installTestVehicle();
        RatePlanComponentTest.installTestRatePlan();
    }

    @AfterClass
    public static void uninstallRequiredObjects() {
        VehicleComponentTest.uninstallTestVehicle();
        RatePlanComponentTest.uninstallTestRatPlan();
        VehicleTypeComponentTest.uninstallTestVehicleType();
    }

    @Before
    public void init() {
        component = CarRentalBeanFactory.getBean(RentalAgreementComponent.class);
        ra1 = generateRentalAgreement();
    }

    @Test
    public void createRentalAgreement() {
        final RentalAgreement ra = installTestRentalAgreement();
        uninstallTestRentalAgreement(ra.getId());
    }

    @Test
    public void createRentalAgreementWithNullVehicle() {
        ra1.setVehicle(null);
        final Result<RentalAgreement> result = component.createRentalAgreement(ra1);
        assertFalse(result.isSuccess());
        assertTrue(result.iterator().next().getClazz() == Vehicle.class);
    }

    @Test
    public void createRentalAgreementWithTooManyAuthorizedDrivers() {
        ra1.addAuthorizedDriver(generateAuthorizedOperator());
        ra1.addAuthorizedDriver(generateAuthorizedOperator());
        ra1.addAuthorizedDriver(generateAuthorizedOperator());
        ra1.addAuthorizedDriver(generateAuthorizedOperator());
        ra1.addAuthorizedDriver(generateAuthorizedOperator());
        final Result<RentalAgreement> result = component.createRentalAgreement(ra1);
        assertFalse("Expected Failure", result.isSuccess());
        assertTrue(ra1.getAuthorizedOperators().getValidationStatus() == ValidationStatus.invalidRange);
    }

    @Test
    public void createRenatlAgreementWithNullRenter() {
        ra1.setRenter(null);
        final Result<RentalAgreement> result = component.createRentalAgreement(ra1);
        assertFalse("Expected Failure", result.isSuccess());
        assertTrue(result.iterator().next().getClazz() == Renter.class);
    }

    @Test
    public void createRentalAgreementInvalidRenter() {
        ra1.getRenter().getDriversLicense().setNumber("OO");
        final Result<RentalAgreement> result = component.createRentalAgreement(ra1);
        assertFalse("Expected Failure", result.isSuccess());
        assertTrue(ra1.getRenter().getDriversLicense().getNumber().getValidationStatus() == ValidationStatus.invalidIllformed);
    }

    @Test(expected = ObjectDoesNotExist.class)
    public void createRentalAgreementWithMissingVehicleType() {
        RatePlanComponentTest.installTestRatePlan();

        try {
            getRentalAgreementComponent().createRentalAgreement(ra1);
        } finally {
            RatePlanComponentTest.uninstallTestRatPlan();
        }
    }

    @Test(expected = ObjectDoesNotExist.class)
    public void createRentalAgreementWithMissingRatePlan() {
        VehicleTypeComponentTest.installTestVehicleType();

        try {
            getRentalAgreementComponent().createRentalAgreement(ra1);
        } finally {
            VehicleTypeComponentTest.uninstallTestVehicleType();
        }

    }
}

package vehicle.integration;

import static junit.framework.Assert.assertNotNull;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import vehicle.component.rateplan.RatePlanComponentTest;
import vehicle.component.vehicle.VehicleComponentTest;
import vehicle.component.vehicletype.VehicleTypeComponentTest;
import vehicle.configuration.CarRentalBeanFactory;
import vehicle.domain.RentalAgreement;
import vehicle.domain.RentalAgreementTest;
import vehicle.domain.Vehicle;
import vehicle.domain.VehicleTest;
import vehicle.exception.ObjectDoesNotExist;
import vehicle.exception.ObjectExists;

public class IRentalAgreementDaoTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(IRentalAgreementDaoTest.class);
    }

    private static IRentalAgreementDao dao;
    private RentalAgreement ra1;

    @BeforeClass
    public static void initClass() {
        dao = CarRentalBeanFactory.getBean(IRentalAgreementDao.class);
    }

    private static RentalAgreement generateRentalAgreement() {
        return RentalAgreementTest.generateRentalAgreement();
    }

    private static Vehicle generateVehicle() {
        return VehicleTest.generateVehicle();
    }

    @Before
    public void generateRa() {
        ra1 = generateRentalAgreement();
        VehicleTypeComponentTest.installTestVehicleType();
        VehicleComponentTest.installTestVehicle();
        RatePlanComponentTest.installTestRatePlan();
    }

    @After
    public void uninstall() {
        RatePlanComponentTest.uninstallTestRatPlan();
        VehicleComponentTest.uninstallTestVehicle();
        VehicleTypeComponentTest.uninstallTestVehicleType();
    }

    @Test
    public void createRentalAgreement() {
        try {
            dao.createRentalAgreement(ra1);
            final RentalAgreement result = dao.getRentalAgreement(ra1.getId());
            assertNotNull("RentalRecord was not inserted", result);
        } finally {
            dao.removeRentalAgreement(ra1.getId());
        }
    }

    @Test(expected = ObjectExists.class)
    public void createRentalAgreementWithDuplicateId() {
        try {
            dao.createRentalAgreement(ra1);
            dao.createRentalAgreement(ra1);
        } finally {
            dao.removeRentalAgreement(ra1.getId());
        }
    }

    @Test
    public void removeRentalAgreement() {
        dao.createRentalAgreement(ra1);
        dao.removeRentalAgreement(ra1.getId());
    }

    @Test(expected = ObjectDoesNotExist.class)
    public void removeMissingRa() {
        dao.removeRentalAgreement(ra1.getId());
    }

    @Test
    public void updateRaInMemoryDoesNotUpdateBackend() {
        dao.createRentalAgreement(ra1);
        try {
            final RentalAgreement ra2 = dao.getRentalAgreement(ra1.getId());
            final Vehicle vehicle = generateVehicle();
            vehicle.setLicense(null);
            ra2.setVehicle(vehicle);
            final RentalAgreement ra3 = dao.getRentalAgreement(ra1.getId());
            assertNotNull(ra3.getVehicle().getLicense());
        } finally {
            dao.removeRentalAgreement(ra1.getId());
        }
    }

    @Test
    public void updateRaAfterCreateDoesNotChangeOriginal() {
        dao.createRentalAgreement(ra1);
        try {
            final Vehicle vehicle = generateVehicle();
            vehicle.setLicense(null);
            ra1.setVehicle(vehicle);
            final RentalAgreement ra2 = dao.getRentalAgreement(ra1.getId());
            Assert.assertNotNull(ra2.getVehicle().getLicense());
        } finally {
            dao.removeRentalAgreement(ra1.getId());
        }
    }
}

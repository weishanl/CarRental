package vehicle.component.vehicle;

import junit.framework.JUnit4TestAdapter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import vehicle.component.vehicletype.VehicleTypeComponentTest;
import vehicle.configuration.CarRentalBeanFactory;
import vehicle.domain.Vehicle;
import vehicle.domain.VehicleLicense;
import vehicle.domain.VehicleTest;
import vehicle.domain.VehicleType;
import vehicle.exception.ObjectDoesNotExist;
import vehicle.type.Result;

public class VehicleComponentTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(VehicleComponentTest.class);
    }

    private static final String INVALID_LICENSE_NUMBER = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final int INVALID_VEHICLE_YEAR = 1967;
    private static Vehicle installedVehicle;
    private static boolean testVehicleInstalled;

    private Vehicle vehicle;

    public static final VehicleComponent getVehicleComponent() {
        return CarRentalBeanFactory.getBean(VehicleComponent.class);
    }

    private static Vehicle generateVehicle() {
        return VehicleTest.generateVehicle();
    }

    private static Result<Vehicle> createAndValidate(final Vehicle vehicle,
            final boolean shouldBeSuccessful) {
        final Result<Vehicle> result = getVehicleComponent().createVehicle(vehicle);
        Assert.assertEquals(shouldBeSuccessful, result.isSuccess());
        return result;
    }

    public static Vehicle installTestVehicle() {
        if (!testVehicleInstalled) {
            final Result<Vehicle> result = createAndValidate(generateVehicle(), true);
            installedVehicle = result.getValue();
            testVehicleInstalled = true;
        }

        return installedVehicle;
    }

    public static final void uninstallTestVehicle() {
        if (testVehicleInstalled) {
            final Result<Vehicle> result = getVehicleComponent().removeVehicle(
                    generateVehicle().getLicense());
            Assert.assertTrue(result.isSuccess());
            testVehicleInstalled = false;
        }
    }

    @Before
    public void init() {
        vehicle = generateVehicle();
    }

    @Test(expected = ObjectDoesNotExist.class)
    public void insertVehicleWithMissingVehicleType() {
        installTestVehicle();
    }

    @Test
    public void testInstallAndUninstall() {
        try {
            VehicleTypeComponentTest.installTestVehicleType();
            installTestVehicle();
        } finally {
            uninstallTestVehicle();
            VehicleTypeComponentTest.uninstallTestVehicleType();
        }
    }

    @Test
    public void insertWithInvalidColor() {
        vehicle.setColor("");
        final Result<Vehicle> result = getVehicleComponent().createVehicle(vehicle);
        Assert.assertFalse(result.isSuccess());
        Assert.assertFalse(result.getValue().getColor().isValid());
    }

    private void attempInvalidCreate(final Vehicle vehicle, final Class clazz) {
        final Result<Vehicle> result = getVehicleComponent().createVehicle(vehicle);
        Assert.assertFalse(result.isSuccess());
        Assert.assertTrue(result.iterator().next().getClazz() == clazz);
    }

    @Test
    public void insertWithNullLicense() {
        vehicle.setLicense(null);
        attempInvalidCreate(vehicle, VehicleLicense.class);
    }

    @Test
    public void insertWithNullVehicleType() {
        vehicle.setType(null);
        attempInvalidCreate(vehicle, VehicleType.class);
    }

    @Test
    public void insertWithInvalidLicense() {
        vehicle.getLicense().setNumber(INVALID_LICENSE_NUMBER);
        createAndValidate(vehicle, false);
        Assert.assertFalse(vehicle.getLicense().getNumber().isValid());
    }

    @Test
    public void insertWithInvalidType() {
        vehicle.getType().setYear(INVALID_VEHICLE_YEAR);
        createAndValidate(vehicle, false);
        Assert.assertFalse(vehicle.getType().getYear().isValid());
    }

}

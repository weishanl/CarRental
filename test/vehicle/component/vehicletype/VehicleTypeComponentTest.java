package vehicle.component.vehicletype;

import java.util.ArrayList;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import vehicle.component.rateplan.RatePlanComponentTest;
import vehicle.component.vehicle.VehicleComponentTest;
import vehicle.configuration.CarRentalBeanFactory;
import vehicle.domain.RatePlan;
import vehicle.domain.ValidState;
import vehicle.domain.Vehicle;
import vehicle.domain.VehicleType;
import vehicle.domain.VehicleTypeTest;
import vehicle.exception.ObjectDoesNotExist;
import vehicle.exception.ObjectExists;
import vehicle.exception.ObjectInUse;
import vehicle.type.Result;

public class VehicleTypeComponentTest extends Assert {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(VehicleTypeComponentTest.class);
    }

    private static final String PRE_EXISTING_2 = "PreExisting2";
    private static final String PRE_EXISTING_1 = "PreExisting1";
    private static final String PRE_EXISTING_4 = "PreExisting4";
    private static final String PRE_EXISTING_3 = "PreExisting3";
    private static final String TEST_1 = "Test1";
    private static final String MAKE = "make";
    private static final String MODEL = "model";
    private static final int YEAR = 2006;

    private VehicleTypeComponent component;

    private static VehicleType testVehicleType;
    private static boolean testVehicleTypeInstalled;
    private static List<VehicleType> installedTypes = new ArrayList<VehicleType>();

    public static VehicleTypeComponent getVehicleTypeComponent() {
        return CarRentalBeanFactory.getBean(VehicleTypeComponent.class);
    }

    public static void installTestVehicleType(final VehicleType vt) {
        final Result<VehicleType> result = getVehicleTypeComponent().createVehicleType(vt);
        assertTrue(result.isSuccess());
        installedTypes.add(vt);
    }

    public static void uninstallAllTestVehicleTypes() {
        for (final VehicleType type : installedTypes) {
            getVehicleTypeComponent().removeVehicleTypeNamed(type.getName().getValue());
        }
        installedTypes.clear();
    }

    public static VehicleType installTestVehicleType() {
        if (!testVehicleTypeInstalled) {
            final VehicleType type = VehicleTypeTest.generateVehicleType();
            final Result<VehicleType> result = getVehicleTypeComponent().createVehicleType(type);
            Assert.assertTrue(result.isSuccess());
            testVehicleType = type;
            testVehicleTypeInstalled = true;
        }

        return testVehicleType;
    }

    public static void uninstallTestVehicleType() {
        if (testVehicleTypeInstalled) {
            getVehicleTypeComponent().removeVehicleTypeNamed(testVehicleType.getName().getValue());
            testVehicleTypeInstalled = false;
            testVehicleType = null;
        }
    }

    private VehicleType createVehicleType(final String name, final String make, final String model,
            final int year, final ValidState valid) {
        return new VehicleType(name, make, model, year, valid);
    }

    private VehicleType createAndInstallVehicleType(final String name, final String make,
            final String model, final int year, final ValidState valid) {
        final VehicleType vehicleType = createVehicleType(name, make, model, year, valid);
        final Result<VehicleType> result = component.createVehicleType(vehicleType);
        assertTrue(result.isSuccess());
        return vehicleType;
    }

    @Before
    public void setup() {
        component = CarRentalBeanFactory.getBean(VehicleTypeComponent.class);
        component.removeAll();
        createAndInstallVehicleType(PRE_EXISTING_1, MAKE, MODEL, YEAR, ValidState.valid);
        createAndInstallVehicleType(PRE_EXISTING_2, MAKE, MODEL, YEAR, ValidState.valid);
        createAndInstallVehicleType(PRE_EXISTING_3, MAKE, MODEL, YEAR, ValidState.invalid);
        createAndInstallVehicleType(PRE_EXISTING_4, MAKE, MODEL, YEAR, ValidState.valid);
    }

    @Test
    public void createVehicleTypeWhenNoneExist() {
        component.createVehicleType(createVehicleType(TEST_1, MAKE, MODEL, YEAR, ValidState.valid));
        final VehicleType vehicleType = component.getVehicleTypeNamed(TEST_1).getValue();
        assertEquals(vehicleType.getName().getValue(), TEST_1);
        assertEquals(vehicleType.getState(), ValidState.valid);
    }

    @Test
    public void createVehicleTypeWhenSomeExist() {
        component.createVehicleType(createVehicleType(TEST_1, MAKE, MODEL, YEAR, ValidState.valid));
        final VehicleType vehicleType = component.getVehicleTypeNamed(TEST_1).getValue();
        assertEquals(vehicleType.getName().getValue(), TEST_1);
        assertEquals(vehicleType.getState(), ValidState.valid);
    }

    @Test(expected = ObjectExists.class)
    public void createVehicleTypeWhenNameAlreadyExists() {
        component.createVehicleType(createVehicleType(PRE_EXISTING_1, MAKE, MODEL, YEAR,
                ValidState.valid));

    }

    @Test(expected = ObjectExists.class)
    public void createVehicleTypeWhenInvalidOneExists() {
        component.createVehicleType(createVehicleType(PRE_EXISTING_3, MAKE, MODEL, YEAR,
                ValidState.invalid));
    }

    @Test
    public void invalidteValidVehicleType() {
        VehicleType vehicleType = component.getVehicleTypeNamed(PRE_EXISTING_1).getValue();
        assertEquals(vehicleType.getState(), ValidState.valid);
        component.setVehicleState(PRE_EXISTING_1, ValidState.invalid);
        vehicleType = component.getVehicleTypeNamed(PRE_EXISTING_1).getValue();
        assertEquals(vehicleType.getState(), ValidState.invalid);
    }

    @Test
    public void invalidteInvalidVehicleType() {
        VehicleType vehicleType = component.getVehicleTypeNamed(PRE_EXISTING_3).getValue();
        assertEquals(vehicleType.getState(), ValidState.invalid);
        component.setVehicleState(PRE_EXISTING_1, ValidState.invalid);
        vehicleType = component.getVehicleTypeNamed(PRE_EXISTING_1).getValue();
        assertEquals(vehicleType.getState(), ValidState.invalid);
    }

    @Test(expected = ObjectDoesNotExist.class)
    public void removeUnusedValidVehicleType() {
        component.removeVehicleTypeNamed(PRE_EXISTING_1);
        component.getVehicleTypeNamed(PRE_EXISTING_1);
    }

    @Test(expected = ObjectDoesNotExist.class)
    public void removeUnusedInvalidVehicleType() {
        component.removeVehicleTypeNamed(PRE_EXISTING_3);
        component.getVehicleTypeNamed(PRE_EXISTING_3);
    }

    @Test(expected = ObjectInUse.class)
    public void removeUsedValidType() {
        try {
            installTestVehicleType();
            final Vehicle v = VehicleComponentTest.installTestVehicle();
            component.removeVehicleTypeNamed(v.getType().getName().getValue());
        } finally {
            VehicleComponentTest.uninstallTestVehicle();
            uninstallTestVehicleType();
        }
    }

    @Test(expected = ObjectInUse.class)
    public void removeUsedInvalidType() {
        installTestVehicleType();
        final RatePlan plan = RatePlanComponentTest.installTestRatePlan();

        try {
            final VehicleType vehicleType = plan.getVehicleType();
            final Result<VehicleType> result = component.invalidateVehicleType(vehicleType);
            component.removeVehicleTypeNamed(vehicleType.getName().getValue());
            assertTrue(result.isSuccess());
        } finally {
            RatePlanComponentTest.uninstallTestRatPlan();
            uninstallTestVehicleType();
        }

    }
}

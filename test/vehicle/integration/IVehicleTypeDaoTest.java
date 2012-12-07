package vehicle.integration;

import junit.framework.JUnit4TestAdapter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import vehicle.configuration.CarRentalBeanFactory;
import vehicle.domain.ValidState;
import vehicle.domain.VehicleType;
import vehicle.exception.ObjectExists;

public class IVehicleTypeDaoTest extends Assert {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(IVehicleTypeDaoTest.class);
    }

    private static final String INVALID = "Invalid";
    private static final String TEST = "Test";
    private static final String LUXURY = "Luxury";
    private static final String CLASSIC = "Classic";
    private static final String MAKE = "make";
    private static final String MODEL = "model";
    private static final int YEAR = 2006;

    private IVehicleTypeDao dao;

    @Before
    public void init() {
        dao = CarRentalBeanFactory.getBean(IVehicleTypeDao.class);
        installVehicleTypes();
    }

    private VehicleType makeVehicleTypeNamed(final String name, final ValidState state) {
        return new VehicleType(name, MAKE, MODEL, YEAR, state);

    }

    private VehicleType makeVehicleTypeNamed(final String name) {
        return makeVehicleTypeNamed(name, ValidState.valid);
    }

    private void installVehicleTypes() {
        dao.removeAll();
        dao.addVehicleType(makeVehicleTypeNamed(CLASSIC));
        dao.addVehicleType(makeVehicleTypeNamed(LUXURY));
        dao.addVehicleType(makeVehicleTypeNamed(INVALID, ValidState.invalid));
    }

    @Test
    public void addVehcileWhenNoneOtherExist() {
        dao.removeAll();
        dao.addVehicleType(makeVehicleTypeNamed(TEST));
        assertEquals(dao.size(), 1);
        final VehicleType vehicleType = dao.getVehicleTypeNamed(TEST);
        assertNotNull(vehicleType);
        assertEquals(vehicleType.getName().getValue(), TEST);
        assertEquals(vehicleType.getState(), ValidState.valid);
    }

    @Test
    public void addVehicleWithNoNameConflict() {
        final int originalDaoSize = dao.size();
        dao.addVehicleType(makeVehicleTypeNamed(TEST));
        assertEquals(dao.size(), originalDaoSize + 1);
        final VehicleType vehicleType = dao.getVehicleTypeNamed(TEST);
        assertNotNull(vehicleType);
        assertEquals(vehicleType.getName().getValue(), TEST);
        assertEquals(vehicleType.getState(), ValidState.valid);
    }

    @Test(expected = ObjectExists.class)
    public void addVehicleWithNameConflict() {
        dao.addVehicleType(makeVehicleTypeNamed(TEST));
        dao.addVehicleType(makeVehicleTypeNamed(TEST));
    }

    @Test(expected = ObjectExists.class)
    public void addVehicleWithNameConflictAndVehicleInvalid() {
        dao.addVehicleType(makeVehicleTypeNamed(TEST, ValidState.invalid));
        dao.addVehicleType(makeVehicleTypeNamed(TEST, ValidState.invalid));
    }

    @Test
    public void changeVehicleWithoutUpdate() {
        final VehicleType vehicleType = dao.getVehicleTypeNamed(CLASSIC);
        final ValidState state = vehicleType.getState();
        final ValidState next = calculateNextState(state);
        vehicleType.setState(next);

        final VehicleType vt2 = dao.getVehicleTypeNamed(CLASSIC);
        assertNotSame(vehicleType.getState(), vt2.getState());
    }

    @Test
    public void changeVehicleWithUpdate() {
        final VehicleType vehicleType = dao.getVehicleTypeNamed(CLASSIC);
        final ValidState state = vehicleType.getState();
        final ValidState next = calculateNextState(state);
        vehicleType.setState(next);
        dao.updateVehicleType(vehicleType);

        final VehicleType vt2 = dao.getVehicleTypeNamed(CLASSIC);
        assertNotSame(vehicleType, vt2);
        assertEquals(vehicleType.getState(), vt2.getState());
    }

    private ValidState calculateNextState(final ValidState state) {
        final ValidState[] states = ValidState.values();
        assertTrue(states.length > 1);
        int nextOrdinal = state.ordinal() + 1;
        if (nextOrdinal >= states.length) {
            nextOrdinal = 0;
        }
        return states[nextOrdinal];
    }
}

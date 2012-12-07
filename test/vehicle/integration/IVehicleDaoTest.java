package vehicle.integration;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import vehicle.configuration.CarRentalBeanFactory;
import vehicle.domain.Vehicle;
import vehicle.domain.VehicleTest;
import vehicle.exception.ObjectDoesNotExist;
import vehicle.exception.ObjectExists;

public class IVehicleDaoTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(IVehicleDaoTest.class);
    }

    private Vehicle generatedVehicle;

    public IVehicleDao getVehicleDao() {
        return CarRentalBeanFactory.getBean(IVehicleDao.class);
    }

    private IVehicleTypeDao getVehicleTypeDao() {
        return CarRentalBeanFactory.getBean(IVehicleTypeDao.class);
    }

    @Before
    public void init() {
        generatedVehicle = VehicleTest.generateVehicle();
    }

    @Test
    public void createVehicle() {
        getVehicleTypeDao().addVehicleType(generatedVehicle.getType());

        try {
            getVehicleDao().createVehicle(generatedVehicle);
        } finally {
            getVehicleDao().removeVehicle(generatedVehicle.getLicense());
            getVehicleTypeDao().removeVehiclTypeNamed(
                    generatedVehicle.getType().getName().getValue());
        }
    }

    @Test(expected = ObjectExists.class)
    public void createDuplicateVehicle() {
        getVehicleTypeDao().addVehicleType(generatedVehicle.getType());

        try {
            getVehicleDao().createVehicle(generatedVehicle);
            getVehicleDao().createVehicle(generatedVehicle);
        } finally {
            getVehicleDao().removeVehicle(generatedVehicle.getLicense());
            getVehicleTypeDao().removeVehiclTypeNamed(
                    generatedVehicle.getType().getName().getValue());
        }
    }

    @Test(expected = ObjectDoesNotExist.class)
    public void removeVehicleThatDoesNotExist() {
        getVehicleDao().removeVehicle(generatedVehicle.getLicense());
    }

    @Test(expected = ObjectDoesNotExist.class)
    public void createVehicleWithMissingVehicleType() {
        getVehicleDao().createVehicle(generatedVehicle);
    }

}

package vehicle.integration;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import vehicle.configuration.CarRentalBeanFactory;
import vehicle.domain.RatePlan;
import vehicle.domain.RatePlanTest;
import vehicle.domain.VehicleType;
import vehicle.exception.ObjectDoesNotExist;
import vehicle.exception.ObjectExists;

public class IRatePlanDaoTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(IRatePlanDaoTest.class);
    }

    private RatePlan rp;
    private VehicleType vt;
    private String rpName;

    private static IVehicleTypeDao getVehicleTypeDao() {
        return CarRentalBeanFactory.getBean(IVehicleTypeDao.class);
    }

    public static IRatePlanDao getRatePlanDao() {
        return CarRentalBeanFactory.getBean(IRatePlanDao.class);
    }

    @Before
    public void init() {
        rp = RatePlanTest.generateRatePlan();
        rpName = rp.getName().getValue();
        vt = rp.getVehicleType();
    }

    @Test
    public void createRatePlan() {
        getVehicleTypeDao().addVehicleType(rp.getVehicleType());
        try {
            getRatePlanDao().createRatePlan(rp);
        } finally {
            getRatePlanDao().removeRatePlan(rpName, vt);
            getVehicleTypeDao().removeVehiclTypeNamed(vt.getName().getValue());
        }
    }

    @Test(expected = ObjectDoesNotExist.class)
    public void createRatePlanWithMissingVehicleType() {
        getRatePlanDao().createRatePlan(rp);
    }

    @Test(expected = ObjectExists.class)
    public void createRatePlanThatAlreadyExists() {
        getVehicleTypeDao().addVehicleType(rp.getVehicleType());
        try {
            getRatePlanDao().createRatePlan(rp);
            getRatePlanDao().createRatePlan(rp);
        } finally {
            getRatePlanDao().removeRatePlan(rpName, vt);
            getVehicleTypeDao().removeVehiclTypeNamed(vt.getName().getValue());
        }
    }

    @Test(expected = ObjectDoesNotExist.class)
    public void removeRatePlanThatDoesNotExist() {
        getRatePlanDao().removeRatePlan(rpName, vt);
    }

}

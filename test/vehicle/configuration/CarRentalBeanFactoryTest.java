package vehicle.configuration;

import junit.framework.JUnit4TestAdapter;

import org.junit.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import vehicle.integration.IVehicleDao;
import vehicle.integration.IVehicleTypeDao;

public class CarRentalBeanFactoryTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CarRentalBeanFactoryTest.class);
    }

    @Test
    public void getSeveralBeans() {
        CarRentalBeanFactory.getBean(IVehicleDao.class);
        CarRentalBeanFactory.getBean(IVehicleTypeDao.class);
    }

    @Test(expected = NoSuchBeanDefinitionException.class)
    public void lookupBeanThatDoesNotExist() {
        CarRentalBeanFactory.getBean(CarRentalBeanFactoryTest.class);
    }

}

package vehicle.integration.inmemory;

import vehicle.configuration.CarRentalBeanFactory;
import vehicle.domain.Vehicle;
import vehicle.domain.VehicleLicense;
import vehicle.domain.VehicleType;
import vehicle.exception.ObjectDoesNotExist;
import vehicle.integration.IVehicleDao;
import vehicle.integration.IVehicleTypeDao;

/**
 * This class implements checks that would otherwise happen as a result of
 * foreign key constraint violations in a database.
 */

public final class InMemoryDaoUtil {
    private InMemoryDaoUtil() {
        // I'm a utility class
    }

    public static void checkVehicleExists(final VehicleLicense license) {
        final InMemoryVehicleDao dao = (InMemoryVehicleDao) CarRentalBeanFactory
                .getBean(IVehicleDao.class);
        if (!dao.vehicleExists(license)) {
            throw new ObjectDoesNotExist(Vehicle.class, license);
        }
    }

    public static void checkVehicleTypeExists(final String vehicleTypeName) {
        final InMemoryVehicleTypeDao dao = (InMemoryVehicleTypeDao) CarRentalBeanFactory
                .getBean(IVehicleTypeDao.class);
        if (!dao.vehicleTypeExists(vehicleTypeName)) {
            throw new ObjectDoesNotExist(VehicleType.class, vehicleTypeName);
        }
    }
}

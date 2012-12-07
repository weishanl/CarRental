package vehicle.integration.inmemory;

import java.util.HashMap;
import java.util.Map;

import vehicle.domain.Vehicle;
import vehicle.domain.VehicleLicense;
import vehicle.exception.ObjectDoesNotExist;
import vehicle.exception.ObjectExists;
import vehicle.integration.IVehicleDao;

public class InMemoryVehicleDao implements IVehicleDao {
    private final Map<VehicleLicense, Vehicle> vehicles = new HashMap<VehicleLicense, Vehicle>(47);

    public void createVehicle(final Vehicle vehicle) {
        if (vehicles.containsKey(vehicle.getLicense())) {
            throw new ObjectExists(Vehicle.class, vehicle.getLicense());
        }

        InMemoryDaoUtil.checkVehicleTypeExists(vehicle.getType().getName().getValue());

        vehicles.put(vehicle.getLicense(), vehicle);
    }

    public Vehicle getVehicle(final VehicleLicense license) {
        final Vehicle result = vehicles.get(license);
        if (result == null) {
            throw new ObjectDoesNotExist(Vehicle.class, license);
        }

        return result.clone();
    }

    public boolean vehicleExists(final VehicleLicense license) {
        return vehicles.get(license) != null;
    }

    public void removeVehicle(final VehicleLicense license) {
        final Vehicle v = vehicles.remove(license);
        if (v == null) {
            throw new ObjectDoesNotExist(VehicleLicense.class, license);
        }
    }

    public boolean isVehicleTypeUsed(final String vehicleTypeName) {
        for (final Vehicle vehicle : vehicles.values()) {
            if (vehicle.getType().getName().valueEquals(vehicleTypeName)) {
                return true;
            }
        }
        return false;
    }

}

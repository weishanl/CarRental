package vehicle.integration;

import vehicle.domain.Vehicle;
import vehicle.domain.VehicleLicense;

public interface IVehicleDao {

    void createVehicle(final Vehicle vehicle);

    Vehicle getVehicle(final VehicleLicense license);

    boolean vehicleExists(final VehicleLicense license);

    void removeVehicle(final VehicleLicense license);

}

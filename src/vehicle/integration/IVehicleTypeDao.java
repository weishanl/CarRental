package vehicle.integration;

import vehicle.domain.VehicleType;

public interface IVehicleTypeDao {

    void addVehicleType(VehicleType type);

    void removeAll();

    VehicleType getVehicleTypeNamed(String string);

    int size();

    void updateVehicleType(VehicleType type);

    void removeVehiclTypeNamed(String name);

    boolean vehicleTypeExists(final String value);

}

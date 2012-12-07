package vehicle.component.vehicle;

import vehicle.domain.Vehicle;
import vehicle.domain.VehicleLicense;
import vehicle.domain.VehicleType;
import vehicle.exception.ObjectDoesNotExist;
import vehicle.integration.IVehicleDao;
import vehicle.type.Result;
import vehicle.type.ValidationStatus;
import vehicle.validation.ValidationSupport;

public class VehicleComponent {
    private IVehicleDao dao;

    public IVehicleDao getDao() {
        return dao;
    }

    public final void setDao(final IVehicleDao dao) {
        this.dao = dao;
    }

    private boolean vehicleTypeDoesNotExist(final Result<Vehicle> result) {
        final VehicleType vehicleType = result.getValue().getType();
        return vehicleType != null
                && vehicleType.getValidationStatus() == ValidationStatus.invalidMissing;
    }

    public Result<Vehicle> createVehicle(final Vehicle vehicle) {
        final Result<Vehicle> result = new Result<Vehicle>(vehicle, true);

        ValidationSupport.nullCheckAndValidate(Vehicle.class, vehicle, result);

        if (result.isSuccess()) {
            dao.createVehicle(vehicle);
        } else {
            if (vehicleTypeDoesNotExist(result)) {
                throw new ObjectDoesNotExist(VehicleType.class, vehicle.getType().getName()
                        .getValue());
            }
        }

        return result;
    }

    public Result<Vehicle> retrieveVehicle(final VehicleLicense vehicleLicense) {
        final Result<Vehicle> result = new Result<Vehicle>();

        vehicleLicense.validate(result);
        if (result.isSuccess()) {
            final Vehicle vehicle = dao.getVehicle(vehicleLicense);
            result.setValue(vehicle);
        }

        return result;
    }

    public Result<Vehicle> removeVehicle(final VehicleLicense vehicleLicense) {
        final Result<Vehicle> result = new Result<Vehicle>();
        vehicleLicense.validate(result);
        if (result.isSuccess()) {
            dao.removeVehicle(vehicleLicense);
        }

        return result;

    }

}

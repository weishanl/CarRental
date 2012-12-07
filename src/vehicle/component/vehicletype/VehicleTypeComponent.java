package vehicle.component.vehicletype;

import vehicle.domain.ValidState;
import vehicle.domain.VehicleType;
import vehicle.integration.IVehicleTypeDao;
import vehicle.type.Result;
import vehicle.validation.ValidationSupport;

public class VehicleTypeComponent {
    private IVehicleTypeDao dao;

    public void setDao(final IVehicleTypeDao dao) {
        this.dao = dao;
    }

    public IVehicleTypeDao getDao() {
        return dao;
    }

    public Result<VehicleType> createVehicleType(final VehicleType vehicleType) {
        final Result<VehicleType> result = new Result<VehicleType>(vehicleType, true);

        ValidationSupport.nullCheckAndValidate(VehicleType.class, vehicleType, result);

        if (result.isSuccess()) {
            dao.addVehicleType(vehicleType);
        }

        return result;
    }

    public void removeAll() {
        dao.removeAll();
    }

    public Result<VehicleType> getVehicleTypeNamed(final String name) {
        final Result<VehicleType> result = new Result<VehicleType>();
        final VehicleType type = dao.getVehicleTypeNamed(name);

        result.setValue(type);

        return result;
    }

    public void setVehicleState(final String name, final ValidState state) {
        final VehicleType vehicleType = dao.getVehicleTypeNamed(name);
        vehicleType.setState(state);
        dao.updateVehicleType(vehicleType);
    }

    public void removeVehicleTypeNamed(final String name) {
        dao.removeVehiclTypeNamed(name);
    }

    public Result<VehicleType> invalidateVehicleType(final VehicleType vehicleType) {
        final Result<VehicleType> result = new Result<VehicleType>(vehicleType, true);

        vehicleType.setState(ValidState.invalid);

        ValidationSupport.nullCheckAndValidate(VehicleType.class, vehicleType, result);

        if (result.isSuccess()) {
            dao.updateVehicleType(vehicleType);
        }

        return result;
    }

}

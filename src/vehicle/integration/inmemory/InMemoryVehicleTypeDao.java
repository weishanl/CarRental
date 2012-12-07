package vehicle.integration.inmemory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import vehicle.configuration.CarRentalBeanFactory;
import vehicle.domain.VehicleType;
import vehicle.exception.ObjectDoesNotExist;
import vehicle.exception.ObjectExists;
import vehicle.exception.ObjectInUse;
import vehicle.integration.IRatePlanDao;
import vehicle.integration.IVehicleDao;
import vehicle.integration.IVehicleTypeDao;

public class InMemoryVehicleTypeDao implements IVehicleTypeDao {
    private final List<VehicleType> types;
    private static final int INITIAL_ARRAY_SIZE = 8;

    public InMemoryVehicleTypeDao() {
        types = new ArrayList<VehicleType>(INITIAL_ARRAY_SIZE);
    }

    public void addVehicleType(final VehicleType type) {
        for (VehicleType current : types) {
            if (current.getName().equals(type.getName())) {
                throw new ObjectExists(VehicleType.class, type.getName());
            }
        }
        types.add(type);
    }

    public void removeAll() {
        types.clear();
    }

    private VehicleType getVehicleTypeOrNullNamed(final String vehicleTypeName) {
        for (VehicleType current : types) {
            if (current.getName().getValue().equals(vehicleTypeName)) {
                return current;
            }
        }
        return null;
    }

    public boolean vehicleTypeExists(final String vehicleTypeName) {
        return getVehicleTypeOrNullNamed(vehicleTypeName) != null;
    }

    public VehicleType getVehicleTypeNamed(final String name) {
        final VehicleType current = getVehicleTypeOrNullNamed(name);

        if (current == null) {
            throw new ObjectDoesNotExist(VehicleType.class, name);
        }

        return current.clone();
    }

    public int size() {
        return types.size();
    }

    public void updateVehicleType(final VehicleType vehicleType) {
        final Iterator<VehicleType> iterator = types.iterator();
        while (iterator.hasNext()) {
            final VehicleType next = iterator.next();
            if (vehicleType.getName().equals(next.getName())) {
                iterator.remove();
            }
        }
        addVehicleType(vehicleType);
    }

    public void removeVehiclTypeNamed(final String name) {
        final Iterator<VehicleType> iterator = types.iterator();

        while (iterator.hasNext()) {
            final VehicleType next = iterator.next();
            if (name.equals(next.getName().getValue())) {
                if (isVehicleTypeInUse(name)) {
                    throw new ObjectInUse(VehicleType.class, name);
                }

                iterator.remove();
            }
        }
    }

    // This method is required to get the same effect as a delete from
    // a db when a foreign key constraint exists. None of the DAO's use
    // are injected because this is unique to an in-memory solution.
    private boolean isVehicleTypeInUse(final String vehilceTypeName) {
        return isVehicletypeUsedByVehicle(vehilceTypeName)
                || isVehicleUsedByRatePlans(vehilceTypeName);
    }

    private boolean isVehicleUsedByRatePlans(final String vehicleTypeName) {
        final InMemoryRatePlanDao dao = (InMemoryRatePlanDao) CarRentalBeanFactory
                .getBean(IRatePlanDao.class);
        return dao.isVehicleTypeUsed(vehicleTypeName);
    }

    private boolean isVehicletypeUsedByVehicle(final String vehicleTypeName) {
        final InMemoryVehicleDao dao = (InMemoryVehicleDao) CarRentalBeanFactory
                .getBean(IVehicleDao.class);
        return dao.isVehicleTypeUsed(vehicleTypeName);
    }

}

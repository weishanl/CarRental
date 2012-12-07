package vehicle.integration.inmemory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import vehicle.configuration.CarRentalBeanFactory;
import vehicle.domain.RatePlan;
import vehicle.domain.VehicleType;
import vehicle.exception.ObjectDoesNotExist;
import vehicle.exception.ObjectExists;
import vehicle.exception.ObjectInUse;
import vehicle.integration.IRatePlanDao;
import vehicle.integration.IRentalAgreementDao;

public class InMemoryRatePlanDao implements IRatePlanDao {
    private final List<RatePlan> plans = new ArrayList<RatePlan>();

    public void createRatePlan(final RatePlan rp) {
        for (RatePlan plan : plans) {
            if (matches(rp, plan)) {
                throw new ObjectExists(RatePlan.class, makeKey(rp));
            }
        }

        InMemoryDaoUtil.checkVehicleTypeExists(rp.getVehicleType().getName().getValue());

        plans.add(rp);
    }

    public void removeRatePlan(final String name, final VehicleType vehicleType) {
        final Iterator<RatePlan> iterator = plans.iterator();
        while (iterator.hasNext()) {
            final RatePlan current = iterator.next();

            if (matches(current, name, vehicleType)) {
                // not injected using spring because an underlying database
                // would fail on a remove because of a forign key violation.
                final InMemoryRentalAgreementDao rentalAgreementDao = (InMemoryRentalAgreementDao) CarRentalBeanFactory
                        .getBean(IRentalAgreementDao.class);

                if (rentalAgreementDao.isRatePlanInUse(name, vehicleType.getName().getValue())) {
                    throw new ObjectInUse(RatePlan.class, name + ":"
                            + vehicleType.getName().getValue());
                }

                iterator.remove();
                return;
            }
        }

        throw new ObjectDoesNotExist(RatePlan.class, makeKey(name, vehicleType));
    }

    private boolean matches(final RatePlan lhs, final String name, final VehicleType vehicleType) {
        return lhs.getName().valueEquals(name) && lhs.getVehicleType().equals(vehicleType);
    }

    private boolean matches(final RatePlan lhs, final RatePlan rhs) {
        return lhs.getName().valueEquals(rhs.getName())
                && lhs.getVehicleType().getName().valueEquals(rhs.getVehicleType().getName());
    }

    private String makeKey(final String name, final VehicleType vehicleType) {
        return name + ":" + vehicleType.getName().getValue();
    }

    private String makeKey(final RatePlan rp) {
        return makeKey(rp.getName().getValue(), rp.getVehicleType());
    }

    private RatePlan retrieveRatePlanOrNull(final String name, final String vehicleTypeName) {
        final Iterator<RatePlan> iter = plans.iterator();
        while (iter.hasNext()) {
            final RatePlan current = iter.next();
            if (matches(current, name, vehicleTypeName)) {
                return current;
            }
        }

        return null;
    }

    public RatePlan retrieveRatePlan(final String name, final String vehicleTypeName) {
        final RatePlan current = retrieveRatePlanOrNull(name, vehicleTypeName);

        if (current != null) {
            return current.clone();
        }

        throw new ObjectDoesNotExist(RatePlan.class, makeKey(name, vehicleTypeName));
    }

    public boolean ratePlanExists(final String ratePlanName, final String vehicleTypeName) {
        return retrieveRatePlanOrNull(ratePlanName, vehicleTypeName) != null;
    }

    public void updateRatePlan(final RatePlan rp) {
        final Iterator<RatePlan> iter = plans.iterator();
        while (iter.hasNext()) {
            if (matches(iter.next(), rp)) {
                iter.remove();
                plans.add(rp);
                return;
            }
        }

        throw new ObjectDoesNotExist(RatePlan.class, makeKey(rp));
    }

    private String makeKey(final String name, final String vehicleTypeName) {
        return name + ":" + vehicleTypeName;
    }

    private boolean matches(final RatePlan rp, final String name, final String vehicleTypeName) {
        return rp.getName().valueEquals(name)
                && rp.getVehicleType().getName().valueEquals(vehicleTypeName);
    }

    public void removeAll() {
        plans.clear();
    }

    public boolean ratePlanInUse(final String ratePlanName, final String vehicleTypeName) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isVehicleTypeUsed(final String vehicleTypeName) {
        for (final RatePlan plan : plans) {
            if (plan.getVehicleType().getName().valueEquals(vehicleTypeName)) {
                return true;
            }
        }
        return false;
    }

}

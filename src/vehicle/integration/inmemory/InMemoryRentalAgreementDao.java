package vehicle.integration.inmemory;

import java.util.HashMap;
import java.util.Map;

import vehicle.domain.RatePlan;
import vehicle.domain.RentalAgreement;
import vehicle.exception.ObjectDoesNotExist;
import vehicle.exception.ObjectExists;
import vehicle.integration.IRentalAgreementDao;

public class InMemoryRentalAgreementDao implements IRentalAgreementDao {
    private static long nextId = Long.MIN_VALUE;

    private final Map<Long, RentalAgreement> rentalAgreements = new HashMap<Long, RentalAgreement>();

    private static long generateNextId() {
        return nextId++;
    }

    public void createRentalAgreement(final RentalAgreement ra1) {
        // assign a key if one has not already been assigned, otherwise make
        // sure key is not already used
        if (ra1.getId() == null) {
            ra1.setId(generateNextId());
        }

        // on the off chance someone manually applied a key
        if (rentalAgreements.containsKey(ra1.getId())) {
            throw new ObjectExists(RentalAgreement.class, ra1.getId());
        }

        InMemoryDaoUtil.checkVehicleTypeExists(ra1.getRate().getVehicleType().getName().getValue());
        InMemoryDaoUtil.checkVehicleExists(ra1.getVehicle().getLicense());

        rentalAgreements.put(ra1.getId(), ra1.clone());
    }

    public RentalAgreement getRentalAgreement(final Long id) {
        final RentalAgreement ra = rentalAgreements.get(id);
        if (ra != null) {
            return ra.clone();
        }
        throw new ObjectDoesNotExist(RentalAgreement.class, id);
    }

    public void removeRentalAgreement(final Long id) {
        final RentalAgreement ra = rentalAgreements.remove(id);
        if (ra == null) {
            throw new ObjectDoesNotExist(RentalAgreement.class, id);
        }
    }

    public boolean isRatePlanInUse(final String ratePlanName, final String vehicleTypeName) {
        for (RentalAgreement current : rentalAgreements.values()) {
            final RatePlan ratePlan = current.getRate();
            if (ratePlan.getName().valueEquals(ratePlanName)
                    && ratePlan.getVehicleType().getName().valueEquals(vehicleTypeName)) {
                return true;
            }
        }
        return false;
    }
}

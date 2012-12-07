package vehicle.integration;

import vehicle.domain.RatePlan;
import vehicle.domain.VehicleType;

public interface IRatePlanDao {

    void createRatePlan(RatePlan ratePlan);

    void removeRatePlan(String name, VehicleType type);

    RatePlan retrieveRatePlan(String name, String vehicleTypeName);

    void updateRatePlan(RatePlan ratePlan);

    void removeAll();

    boolean ratePlanExists(final String ratePlanName, final String vehicleTypeName);

    boolean ratePlanInUse(final String ratePlanName, String vehicleTypeName);

}

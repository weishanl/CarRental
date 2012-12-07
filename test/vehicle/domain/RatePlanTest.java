package vehicle.domain;

import junit.framework.Assert;
import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import vehicle.type.Result;

public class RatePlanTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(RatePlanTest.class);
    }

    private static final String NAME = "Test Rate Plan";

    private static VehicleType generateVehicleType() {
        return VehicleTypeTest.generateVehicleType();
    }

    private static RateSchedule generateRateSchedule() {
        return RateScheduleTest.generateRateSchedule();
    }

    public static RatePlan generateRatePlan() {
        return new RatePlan(NAME, generateRateSchedule(), generateVehicleType(), ValidState.valid);
    }

    @Test
    public void createAndValidate() {
        final RatePlan plan = generateRatePlan();
        final Result<RatePlan> result = new Result<RatePlan>(plan, true);
        plan.validate(result);
        Assert.assertTrue(result.isSuccess());
    }
}

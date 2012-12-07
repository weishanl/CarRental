package vehicle.domain;

import junit.framework.Assert;
import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import vehicle.type.Result;

public class VehicleTypeTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(VehicleTypeTest.class);
    }

    public static final String TYPE_NAME = "Vehicle Type";
    private static final String MAKE = "make";
    private static final String MODEL = "model";
    private static final int YEAR = 2006;

    public static VehicleType generateVehicleType() {
        return new VehicleType(TYPE_NAME, MAKE, MODEL, YEAR, ValidState.valid);
    }

    @Test
    public void validateVehicleType() {
        final VehicleType type = generateVehicleType();
        final Result<VehicleType> result = new Result<VehicleType>(type, true);
        type.validate(result);
        Assert.assertTrue(result.isSuccess());
    }
}

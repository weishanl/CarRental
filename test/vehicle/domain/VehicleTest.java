package vehicle.domain;

import junit.framework.JUnit4TestAdapter;

import org.junit.Assert;
import org.junit.Test;

import vehicle.type.Result;

public class VehicleTest extends Assert {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(VehicleTest.class);
    }

    private static final String COLOR = "Green";

    public static Vehicle generateVehicle() {
        return new Vehicle(VehicleTypeTest.generateVehicleType(), COLOR, VehicleLicenseTest
                .generateVehicleLicense());
    }

    @Test
    public void createAndValidate() {
        final Vehicle veh = generateVehicle();
        final Result<Vehicle> result = new Result<Vehicle>(veh, true);
        veh.validate(result);
        assertTrue(result.isSuccess());
    }
}

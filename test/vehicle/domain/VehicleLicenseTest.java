package vehicle.domain;

import junit.framework.Assert;
import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import vehicle.type.Result;

public class VehicleLicenseTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(VehicleLicenseTest.class);
    }

    private static final String STATE_NAME = "IA";
    private static final String LICENSE_NUMBER = "112AJZ";

    public static VehicleLicense generateVehicleLicense() {
        return new VehicleLicense(LICENSE_NUMBER, new IssuingState(STATE_NAME));
    }

    @Test
    public void createAndValidate() {
        final VehicleLicense lic = generateVehicleLicense();
        final Result<VehicleLicense> result = new Result<VehicleLicense>(lic, true);
        lic.validate(result);
        Assert.assertTrue(result.isSuccess());
    }

}

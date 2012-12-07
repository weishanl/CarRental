package vehicle.domain;

import junit.framework.Assert;
import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import vehicle.type.Result;

public class DriversLicenseTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DriversLicenseTest.class);
    }

    private static final int MAX_LAST_FOUR = 9999;
    private static final int MAX_FIRST_THREE = 999;
    private static int nextFirstThree = 0;
    private static int nextLastFour = 0;

    private static String generateNextThree() {
        final int value = nextFirstThree++;

        if (nextFirstThree > MAX_FIRST_THREE) {
            nextFirstThree = 0;
        }

        return String.format("%03d", value);
    }

    private static String generatelastFour() {
        final int value = nextLastFour++;

        if (nextLastFour > MAX_LAST_FOUR) {
            nextLastFour = 0;
        }

        return String.format("%04d", value);
    }

    public static DriversLicense generateDriversLicense() {
        return new DriversLicense(generateNextThree() + "ZQ" + generatelastFour(),
                new IssuingState("IA"));
    }

    @Test
    public void createAndValidate() {
        final DriversLicense license = generateDriversLicense();
        final Result<DriversLicense> result = new Result<DriversLicense>(license, true);
        license.validate(result);
        Assert.assertTrue(result.isSuccess());
    }

}

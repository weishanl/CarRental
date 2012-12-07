package vehicle.domain;

import junit.framework.JUnit4TestAdapter;

import org.junit.Assert;
import org.junit.Test;

import vehicle.type.Result;

public class RenterTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(RenterTest.class);
    }

    private static Address generateAddress() {
        return AddressTest.generateAddress();
    }

    private static DriversLicense generateDriversLicense() {
        return DriversLicenseTest.generateDriversLicense();
    }

    public static Renter generateRenter() {
        return new Renter("Brett Schuchert", generateAddress(), generateDriversLicense());
    }

    @Test
    public void createAndValidate() {
        final Renter renter = generateRenter();
        final Result<Renter> result = new Result<Renter>(renter, true);
        renter.validate(result);
        Assert.assertTrue(result.isSuccess());
    }
}

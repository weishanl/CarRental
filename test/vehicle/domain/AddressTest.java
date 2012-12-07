package vehicle.domain;

import junit.framework.Assert;
import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import vehicle.type.Result;

public class AddressTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AddressTest.class);
    }

    public static Address generateAddress() {
        return new Address("5080 Spectrum drive", "suite 1010 West", "Dallas", "TX", "75001",
                AddressType.business, new Phone("927", "789", "1200"));
    }

    @Test
    public void createAndValidate() {
        final Address address = generateAddress();
        final Result<Address> result = new Result<Address>(address, true);
        address.validate(result);
        Assert.assertTrue(result.isSuccess());
    }

}

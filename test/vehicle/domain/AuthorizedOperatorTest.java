package vehicle.domain;

import junit.framework.Assert;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import vehicle.type.Result;

public class AuthorizedOperatorTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AuthorizedOperatorTest.class);
    }

    private AuthorizedOperator operator;

    private static DriversLicense generateDriversLicense() {
        return DriversLicenseTest.generateDriversLicense();
    }

    public static AuthorizedOperator generateAuthorizedOperator() {
        return new AuthorizedOperator("Brett", generateDriversLicense());
    }

    @Before
    public void init() {
        operator = generateAuthorizedOperator();
    }

    @Test
    public void testValidAuthorizedOperator() {
        final Result<AuthorizedOperator> result = new Result<AuthorizedOperator>(operator, true);
        operator.validate(result);
        Assert.assertTrue(result.isSuccess());
    }

    @Test
    public void testNullName() {
        operator.setName(null);
        final Result<AuthorizedOperator> result = new Result<AuthorizedOperator>(operator, true);
        operator.validate(result);
        Assert.assertFalse(result.isSuccess());
    }

    @Test
    public void testNullDriversLicense() {
        operator.setDriversLicense(null);
        final Result<AuthorizedOperator> result = new Result<AuthorizedOperator>(operator, true);
        operator.validate(result);
        Assert.assertFalse(result.isSuccess());
    }
}

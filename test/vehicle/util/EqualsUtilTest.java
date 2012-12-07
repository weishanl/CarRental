package vehicle.util;

import junit.framework.JUnit4TestAdapter;

import org.junit.Assert;
import org.junit.Test;

public class EqualsUtilTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(EqualsUtilTest.class);
    }

    @Test
    public void nullNullTest() {
        Assert.assertTrue(EqualsUtil.equals(null, null)); // NOPMD
    }

    @Test
    public void nullNotNullTest() {
        Assert.assertFalse(EqualsUtil.equals(null, "")); // NOPMD
    }

    @Test
    public void notNullNullTest() {
        Assert.assertFalse(EqualsUtil.equals("", null)); // NOPMD
    }

    @Test
    public void notNullNotNullTest() {
        Assert.assertTrue(EqualsUtil.equals("", "")); // NOPMD
    }
}

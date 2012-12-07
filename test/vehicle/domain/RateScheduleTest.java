package vehicle.domain;

import junit.framework.Assert;
import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

import vehicle.type.Result;

public class RateScheduleTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(RateScheduleTest.class);
    }

    private static final float DAILY = 24;
    private static final float WEEKLY = 120;
    private static final float ADDITIONAL_HOUR = 8;
    private static final float ADDITIONAL_DAY = 32;
    private static final float AUTHORIZED_DRIVER = 7;

    public static RateSchedule generateRateSchedule() {
        return new RateSchedule(DAILY, WEEKLY, AUTHORIZED_DRIVER, ADDITIONAL_DAY, ADDITIONAL_HOUR);
    }

    @Test
    public void createAndValidate() {
        final RateSchedule schedule = generateRateSchedule();
        final Result<RateSchedule> result = new Result<RateSchedule>(schedule, true);
        schedule.validate(result);
        Assert.assertTrue(result.isSuccess());
    }

}

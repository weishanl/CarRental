package vehicle.util;

import java.util.Calendar;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

public class TimeBombTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(TimeBombTest.class);
    }

    private int day;
    private int month;
    private int year;

    @Before
    public void init() {
        final Calendar now = Calendar.getInstance();
        day = now.get(Calendar.DAY_OF_MONTH);
        month = now.get(Calendar.MONTH) + 1;
        year = now.get(Calendar.YEAR);
    }

    @Test(expected = RuntimeException.class)
    public void notExpired() {
        TimeBomb.throwUntil(day + 1, month, year, new RuntimeException()); // NOPMD
    }

    @Test
    public void expired() {
        TimeBomb.throwUntil(day, month, year - 1, new RuntimeException()); // NOPMD
    }
}

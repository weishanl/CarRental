package vehicle.util;

import java.util.Calendar;

public final class TimeBomb {
    private TimeBomb() {
        // do not allow instantation, I am a utility class
    }

    public static <T extends Exception> void throwUntil(final int d, final int m, final int y,
            final T e) throws T {
        final Calendar now = Calendar.getInstance();
        final Calendar dateToThrowUntil = Calendar.getInstance();
        dateToThrowUntil.set(y, m - 1, d);

        if (dateToThrowUntil.after(now)) {
            throw e;
        }
    }
}

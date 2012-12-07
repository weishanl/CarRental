package vehicle.util;

public final class EqualsUtil {
    private EqualsUtil() {
        // do not instantiate, I'm a utility class
    }

    public static boolean equals(final Object lhs, final Object rhs) {
        if (lhs == null && rhs == null) {
            return true;
        }
        if (lhs == null && rhs != null) {
            return false;
        }
        if (lhs != null && rhs == null) {
            return false;
        }
        return lhs.equals(rhs);
    }

}

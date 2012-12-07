package vehicle.exception;

public class ObjectInIncorrectState extends RuntimeException {
    private static final long serialVersionUID = 7265230353391342821L;

    public ObjectInIncorrectState(final Class clazz, final Object key, final Object inState,
            final Object expectedState) {
        super(clazz.getName() + " with key: " + key + " in state: " + inState
                + " but expected state: " + expectedState);
    }
}

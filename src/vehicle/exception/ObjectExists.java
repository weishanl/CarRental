package vehicle.exception;

public class ObjectExists extends RuntimeException {
    private static final long serialVersionUID = 5366245154088911395L;

    public ObjectExists(final Class clazz, final Object key) {
        super(clazz.getName() + " with key: " + key + " already exists");
    }
}

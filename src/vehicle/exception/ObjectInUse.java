package vehicle.exception;

public class ObjectInUse extends RuntimeException {
    private static final long serialVersionUID = -7693201991855801098L;

    public ObjectInUse(final Class clazz, final Object key) {
        super(clazz.getName() + " with key: " + key + " is in use and cannot be removed");
    }
}

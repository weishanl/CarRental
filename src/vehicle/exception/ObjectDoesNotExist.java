package vehicle.exception;

public class ObjectDoesNotExist extends RuntimeException {
    private static final long serialVersionUID = -7693201991855801098L;

    public ObjectDoesNotExist(final Class clazz, final Object key) {
        super(clazz.getName() + " with key: " + key + " does not exists");
    }
}

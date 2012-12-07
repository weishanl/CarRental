package vehicle.type;

import java.io.Serializable;

public final class ClassAndMessage implements Serializable {
    private static final long serialVersionUID = -4406055812784627793L;

    private Class clazz;
    private String message;

    public ClassAndMessage(final Class clazz, final String message) {
        setClazz(clazz);
        setMessage(message);
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(final Class clazz) {
        this.clazz = clazz;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" + clazz.getName() + ", " + message + "}";
    }

}

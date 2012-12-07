package vehicle.type;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Result<T> {
    private T value;
    private boolean success;
    private final List<ClassAndMessage> globalErrors = new ArrayList<ClassAndMessage>(3);

    public Result() {
        setSuccess(true);
    }

    public Result(final T value, final boolean success) {
        setValue(value);
        setSuccess(success);
    }

    public final T getValue() {
        return value;
    }

    public final void setValue(final T value) {
        this.value = value;
    }

    public final boolean isSuccess() {
        return success;
    }

    public final void setSuccess(final boolean success) {
        this.success = success;
    }

    public void addGlobalError(final Class fieldClass, final String string) {
        globalErrors.add(new ClassAndMessage(fieldClass, string));
    }

    public Iterator<ClassAndMessage> iterator() {
        return globalErrors.iterator();
    }

    public List<ClassAndMessage> getGlobalErrors() {
        return globalErrors;
    }
}

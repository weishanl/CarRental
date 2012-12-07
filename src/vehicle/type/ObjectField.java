package vehicle.type;

import vehicle.domain.Cloneable;

public class ObjectField<T extends Cloneable> extends AbstractField<T> {
    private static final long serialVersionUID = 6975704936748809345L;

    private T value;

    public ObjectField(final T initialValue) {
        this.value = initialValue;
    }

    @SuppressWarnings("unchecked")
    public ObjectField<T> clone() {
        ObjectField<T> result = (ObjectField<T>) super.clone();
        result.value = (T) value.clone();
        return result;
    }

    public T getValue() {
        return value;
    }

    @Override
    protected void setValueImpl(final T value) {
        this.value = value;
    }
}

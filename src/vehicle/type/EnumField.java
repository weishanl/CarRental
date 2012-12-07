package vehicle.type;

public class EnumField<T> extends AbstractField<T> {
    private static final long serialVersionUID = 201193261712078789L;
    private T value;

    public EnumField() {
        // allow for default construction
    }

    public EnumField(final T value) {
        setValue(value);
    }

    public EnumField<T> clone() {
        return (EnumField<T>) super.clone();
    }

    @Override
    protected void setValueImpl(final T value) {
        this.value = value;
    }

    @Override
    public T getValue() {
        return value;
    }

}

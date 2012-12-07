package vehicle.type;

import vehicle.validation.AbstractFieldValidator;

public class IntegerField extends AbstractField<Integer> {
    private static final long serialVersionUID = 201193261712078789L;
    private int value;

    public IntegerField(final int value, final AbstractFieldValidator validator) {
        super(validator);
        setValue(value);
    }

    @Override
    protected void setValueImpl(final Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

}

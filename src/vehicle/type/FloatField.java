package vehicle.type;

import vehicle.validation.AbstractFieldValidator;

public class FloatField extends AbstractField<Float> {
    private static final long serialVersionUID = 201193261712078789L;
    private float value;

    public FloatField() {
        // allow for default construction
    }

    public FloatField(final AbstractFieldValidator<FloatField> validator) {
        super(validator);
    }

    public FloatField(final float value, final AbstractFieldValidator<FloatField> validator) {
        super(validator);
        setValue(value);
    }

    @Override
    protected void setValueImpl(final Float value) {
        this.value = value;
    }

    @Override
    public Float getValue() {
        return value;
    }

    @Override
    public FloatField clone() {
        return (FloatField) super.clone();
    }

}

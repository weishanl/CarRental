package vehicle.type;

import vehicle.validation.AbstractFieldValidator;

public class StringField extends AbstractField<String> {
    private static final long serialVersionUID = 201193261712078789L;
    private String value;

    public StringField() {
        value = "";
    }

    public StringField(final String value, final AbstractFieldValidator<StringField> validator) {
        super(validator);
        setValue(value);
    }

    @Override
    protected void setValueImpl(final String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    public StringField clone() {
        return (StringField) super.clone();
    }

    public String toString() {
        return "{\"" + value + "\", " + getChangedStatus() + ", " + getValidationStatus() + "}";
    }

}

package vehicle.domain;

import vehicle.type.AbstractObject;
import vehicle.type.Result;
import vehicle.type.StringField;
import vehicle.validation.StateValidator;
import vehicle.validation.ValidationSupport;

public class IssuingState extends AbstractObject<IssuingState> {
    private static final long serialVersionUID = -7880678501181272915L;
    private StringField name;

    @Override
    public IssuingState clone() {
        return (IssuingState) super.clone();
    }

    public IssuingState(final String name) {
        setName(new StringField(name, StateValidator.INSTANCE));
    }

    public final StringField getName() {
        return name;
    }

    public final void setName(final StringField name) {
        this.name = name;
    }

    @Override
    public final int hashCode() {
        return name.hashCode();
    }

    @Override
    public final boolean equals(final Object rhs) {
        if (rhs instanceof IssuingState) {
            return name.valueEquals(((IssuingState) rhs).name);
        }
        return false;
    }

    public void validate(final Result<?> result) {
        ValidationSupport.nullCheckAndValidate(StringField.class, getName(), result);
    }
}

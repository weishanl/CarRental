package vehicle.validation;

import vehicle.type.ValidationStatus;
import vehicle.type.Result;
import vehicle.type.StringField;

public class StringLength extends AbstractFieldValidator<StringField> {
    public static final StringLength LENGTH_3 = new StringLength(3);

    private final int length;

    public StringLength(final int length) {
        this.length = length;
    }

    @Override
    protected void validateChangedAndNotYetValidated(final StringField field, final Result<?> result) {
        if (field.getValue() == null || field.getValue().length() != length) {
            field.setValidationStatus(ValidationStatus.invalidIllformed);
            result.setSuccess(false);
        }
    }
}

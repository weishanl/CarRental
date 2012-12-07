package vehicle.validation;

import vehicle.type.FloatField;
import vehicle.type.Result;
import vehicle.type.ValidationStatus;

public class FloatIsGreaterThanOrEqualToZero extends AbstractFieldValidator<FloatField> {
    public static final FloatIsGreaterThanOrEqualToZero INSTANCE = new FloatIsGreaterThanOrEqualToZero();

    @Override
    protected void validateChangedAndNotYetValidated(final FloatField field, final Result<?> result) {
        field.setValidationStatus(ValidationStatus.valid);
        if (field.getValue() < 0) {
            field.setValidationStatus(ValidationStatus.invalidRange);
            result.setSuccess(false);
        }
    }
}

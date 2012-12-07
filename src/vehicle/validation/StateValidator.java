package vehicle.validation;

import vehicle.reference.IsoStateCodes;
import vehicle.type.ValidationStatus;
import vehicle.type.Result;
import vehicle.type.StringField;

public class StateValidator extends AbstractFieldValidator<StringField> {
    public static final StateValidator INSTANCE = new StateValidator();

    @Override
    protected void validateChangedAndNotYetValidated(final StringField field, final Result<?> result) {
        field.setValidationStatus(ValidationStatus.valid);

        if (!IsoStateCodes.INSTANCE.isValidCode(field.getValue())) {
            field.setValidationStatus(ValidationStatus.invalidIllformed);
            result.setSuccess(false);
        }
    }

}

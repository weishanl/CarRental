package vehicle.validation;

import vehicle.type.AbstractField;
import vehicle.type.ValidationStatus;
import vehicle.type.Result;

public class IntegerGreaterThanValidator extends AbstractFieldValidator<AbstractField<Integer>> {
    private final int value;

    public IntegerGreaterThanValidator(final int value) {
        this.value = value;
    }

    @Override
    protected void validateChangedAndNotYetValidated(final AbstractField<Integer> field, final Result result) {
        field.setValidationStatus(ValidationStatus.valid);

        if (field.getValue() <= value) {
            field.setValidationStatus(ValidationStatus.invalidRange);
            result.setSuccess(false);
        }

    }

}

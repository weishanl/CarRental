package vehicle.validation;

import vehicle.type.AbstractField;
import vehicle.type.ValidationStatus;
import vehicle.type.Result;

public class FloatIsGreaterThanZero extends AbstractFieldValidator<AbstractField<Float>> {
	public static final FloatIsGreaterThanZero INSTANCE = new FloatIsGreaterThanZero();

	@Override
	protected void validateChangedAndNotYetValidated(final AbstractField<Float> field,
			final Result<?> result) {
		field.setValidationStatus(ValidationStatus.valid);
		if (field.getValue() <= 0) {
			field.setValidationStatus(ValidationStatus.invalidRange);
			result.setSuccess(false);
		}
	}
}

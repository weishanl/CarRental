package vehicle.validation;

import vehicle.type.ListField;
import vehicle.type.Result;
import vehicle.type.ValidationStatus;

public class MultiplicityValidator<T> extends AbstractFieldValidator<ListField<?>> {
    public static final int NO_LIMIT = -1;

    public static final MultiplicityValidator ONE_TO_MANY = new MultiplicityValidator(1, NO_LIMIT);
    public static final MultiplicityValidator ZERO_TO_MANY = new MultiplicityValidator(0, NO_LIMIT);

    private final int lowRange;
    private final int highRange;

    public MultiplicityValidator(final int lowRange, final int highRange) {
        this.lowRange = lowRange;
        this.highRange = highRange;
    }

    @Override
    protected void validateChangedAndNotYetValidated(final ListField<?> field,
            final Result<?> result) {
        final int size = field.getValue().size();

        field.setValidationStatus(ValidationStatus.valid);

        if (lowRange != NO_LIMIT && size < lowRange) {
            field.setValidationStatus(ValidationStatus.invalidRange);
            result.setSuccess(false);
        }

        if (highRange != NO_LIMIT && size > highRange) {
            field.setValidationStatus(ValidationStatus.invalidRange);
            result.setSuccess(false);
        }
    }
}

package vehicle.validation;

import vehicle.type.AbstractField;
import vehicle.type.Result;

public abstract class AbstractFieldValidator<T extends AbstractField<?>> {
    protected abstract void validateChangedAndNotYetValidated(T field, Result<?> result);

    public void validate(final T field, final Result<?> result) {
        if (field.isChanged() && field.isValidOrNotYetValidated()) {
            validateChangedAndNotYetValidated(field, result);
        }
    }
}

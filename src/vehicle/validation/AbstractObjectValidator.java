package vehicle.validation;

import vehicle.type.Result;
import vehicle.type.Validateable;

public abstract class AbstractObjectValidator<T extends Validateable> {
    protected abstract void validateImpl(final T object, final Result<?> result);

    public final void validate(final T object, final Result<?> result) {
        if (result.isSuccess()) {
            validateImpl(object, result);
        }
    }
}

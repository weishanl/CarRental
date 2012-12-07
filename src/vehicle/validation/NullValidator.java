package vehicle.validation;

import vehicle.type.AbstractField;
import vehicle.type.Result;

public class NullValidator extends AbstractFieldValidator<AbstractField<?>> {
    public static final NullValidator INSTANCE = new NullValidator();

    @Override
    protected void validateChangedAndNotYetValidated(final AbstractField field, final Result result) {
        // do nothing
    }

}

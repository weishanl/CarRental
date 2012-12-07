package vehicle.validation;

import vehicle.exception.SystemConfigurationException;
import vehicle.type.AbstractField;
import vehicle.type.AbstractObject;
import vehicle.type.Result;
import vehicle.type.Validateable;

public final class ValidationSupport {
    private ValidationSupport() {
        // Private because I'm a util class
    }

    public static <T extends Validateable> void validateIfSuccess(final T object,
            final Result<?> result, final AbstractObjectValidator<T> validator) {
        nullCheck(object, result, validator);

        if (result.isSuccess()) {
            validator.validate(object, result);
        }
    }

    private static <T extends Validateable> void nullCheck(final T object, final Result<?> result,
            final AbstractObjectValidator<T> validator) {
        if (validator == null) {
            throw new SystemConfigurationException("null validator provided to validateIfSuccess");
        }

        if (result == null) {
            throw new SystemConfigurationException("null result provided to validateIfSuccess");
        }

        if (object == null) {
            result.setSuccess(false);
            result
                    .addGlobalError(validator.getClass(),
                            "Null object provided when using validator");
        }
    }

    public static void nullCheckAndValidate(final Class fieldClass, final AbstractField<?> field,
            final Result<?> result) {
        if (validateClassResultTarget(fieldClass, result, field)) {
            field.validate(result);
        }
    }

    public static <T> void nullCheckAndValidate(final Class targetClass,
            final AbstractObject<?> target, final Result<?> result) {
        if (validateClassResultTarget(targetClass, result, target)) {
            target.validate(result);
        }
    }

    private static boolean validateClassResultTarget(final Class clazz, final Result<?> result,
            final Object target) {
        if (clazz == null) {
            throw new SystemConfigurationException("null clazz passed into validateField");
        }

        if (result == null) {
            throw new SystemConfigurationException("null result passed in to validateField");
        }

        if (target == null) {
            result.setSuccess(false);
            result.addGlobalError(clazz, "Null object provided to validateField");
            return false;
        }

        return true;
    }

    public static final AbstractFieldValidator EMPTY_VALIDATION = new AbstractFieldValidator() {

        @Override
        protected void validateChangedAndNotYetValidated(final AbstractField field,
                final Result result) {
            // do nothing, always pass
        }
    };

}

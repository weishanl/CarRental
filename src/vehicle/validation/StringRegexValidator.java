package vehicle.validation;

import vehicle.type.ValidationStatus;
import vehicle.type.Result;
import vehicle.type.StringField;

public class StringRegexValidator extends AbstractFieldValidator<StringField> {
    private final String regex;

    public StringRegexValidator(final String regex) {
        this.regex = regex;
    }

    @Override
    protected void validateChangedAndNotYetValidated(final StringField field, final Result<?> result) {
        if (!field.getValue().matches(regex)) {
            result.setSuccess(false);
            field.setValidationStatus(ValidationStatus.invalidIllformed);
            return;
        }

        field.setValidationStatus(ValidationStatus.valid);
    }
}

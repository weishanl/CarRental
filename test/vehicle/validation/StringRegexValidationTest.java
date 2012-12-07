package vehicle.validation;

import junit.framework.JUnit4TestAdapter;

import org.junit.Assert;
import org.junit.Test;

import vehicle.type.Result;
import vehicle.type.StringField;
import vehicle.type.ValidationStatus;

public class StringRegexValidationTest {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(StringRegexValidationTest.class);
    }

    private StringField init(final String value,
            final AbstractFieldValidator<StringField> validator, final boolean expectedResults) {
        final StringField field = new StringField(value, validator);
        final Result<StringField> result = new Result<StringField>(field, true);
        ValidationSupport.nullCheckAndValidate(StringField.class, field, result);
        Assert.assertEquals(expectedResults, result.isSuccess());
        return field;
    }

    private void validateAndVerifyFailure(final AbstractFieldValidator<StringField> validator,
            final String value) {
        final StringField field = init(value, validator, false);
        Assert.assertEquals("Incorrect validation state", field.getValidationStatus(),
                ValidationStatus.invalidIllformed);
    }

    private void validateAndVerifySuccess(final AbstractFieldValidator<StringField> validator,
            final String value) {
        final StringField field = init(value, validator, true);
        Assert.assertTrue("Should be valid", field.isValid());
    }

    @Test
    public void validateSimple() {
        final StringRegexValidator validator = new StringRegexValidator("^[2-8][B-Y]$");
        validateAndVerifySuccess(validator, "2B");
        validateAndVerifySuccess(validator, "8B");
        validateAndVerifySuccess(validator, "2Y");
        validateAndVerifySuccess(validator, "8Y");
        validateAndVerifyFailure(validator, "1B");
        validateAndVerifyFailure(validator, "1A");
        validateAndVerifyFailure(validator, "11A");
        validateAndVerifyFailure(validator, "");
    }
}

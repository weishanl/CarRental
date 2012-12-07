package vehicle.validation;

import java.util.ArrayList;

import junit.framework.JUnit4TestAdapter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import vehicle.domain.Phone;
import vehicle.type.ListField;
import vehicle.type.Result;

public class MultiplicityValidatorTest extends Assert {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(MultiplicityValidatorTest.class);
    }

    private ListField<Phone> list;
    private Result<ListField<Phone>> result;

    @Before
    public void init() {
        list = new ListField<Phone>(new ArrayList<Phone>());
        result = new Result<ListField<Phone>>(list, true);
    }

    private void addPhones(final int count) {
        for (int i = 0; i < count; ++i) {
            list.add(new Phone());
        }
    }

    private void validateAndAssertIsNotValid(final MultiplicityValidator validator) {
        list.setValidator(validator);
        ValidationSupport.nullCheckAndValidate(ListField.class, list, result);
        assertFalse("Should be successful", result.isSuccess());
        assertFalse("Should be valid", list.isValid());
    }

    private void validateAndAssertIsValid(final MultiplicityValidator validator) {
        list.setValidator(validator);
        ValidationSupport.nullCheckAndValidate(ListField.class, list, result);
        assertTrue("Should be successful", result.isSuccess());
        assertTrue("Should be valid", list.isValid());
    }

    @Test
    public void oneToMany0() {
        validateAndAssertIsNotValid(MultiplicityValidator.ONE_TO_MANY);
    }

    @Test
    public void oneToMany1() {
        addPhones(1);
        validateAndAssertIsValid(MultiplicityValidator.ONE_TO_MANY);
    }

    @Test
    public void oneToMany3() {
        addPhones(THREE);
        validateAndAssertIsValid(MultiplicityValidator.ONE_TO_MANY);
    }

    @Test
    public void zeroOrMore0() {
        validateAndAssertIsValid(MultiplicityValidator.ZERO_TO_MANY);
    }

    @Test
    public void zeroOrMore1() {
        list.add(new Phone());
        validateAndAssertIsValid(MultiplicityValidator.ZERO_TO_MANY);
    }

    @Test
    public void zeroTo30() {
        final MultiplicityValidator validator = new MultiplicityValidator(0, 3);
        validateAndAssertIsValid(validator);
    }

    @Test
    public void zeroTo33() {
        final MultiplicityValidator validator = new MultiplicityValidator(0, 3);
        addPhones(THREE);
        validateAndAssertIsValid(validator);
    }

    @Test
    public void zeroTo3Hit4() {
        final MultiplicityValidator validator = new MultiplicityValidator(0, 3);
        addPhones(FOUR);
        validateAndAssertIsNotValid(validator);
    }

    @Test
    public void fiveTo10Validate4() {
        final MultiplicityValidator validator = new MultiplicityValidator(5, 10);
        addPhones(FOUR);
        validateAndAssertIsNotValid(validator);
    }

    @Test
    public void fiveTo10Validate11() {
        final MultiplicityValidator validator = new MultiplicityValidator(5, 10);
        addPhones(ELEVEN);
        validateAndAssertIsNotValid(validator);
    }

    @Test
    public void fiveTo10Validate5() {
        final MultiplicityValidator validator = new MultiplicityValidator(5, 10);
        addPhones(FIVE);
        validateAndAssertIsValid(validator);
    }

    @Test
    public void fiveTo10Validate10() {
        final MultiplicityValidator validator = new MultiplicityValidator(5, 10);
        addPhones(TEN);
        validateAndAssertIsValid(validator);
    }

    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int FIVE = 5;
    private static final int TEN = 10;
    private static final int ELEVEN = 11;

}

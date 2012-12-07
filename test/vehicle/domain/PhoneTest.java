package vehicle.domain;

import junit.framework.JUnit4TestAdapter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import vehicle.type.Result;
import vehicle.type.ValidationStatus;

public class PhoneTest extends Assert {
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(PhoneTest.class);
    }

    private static final String SHOULD_HAVE_FAILED = "Should have failed";
    private Result<Phone> result;

    @Before
    public void init() {
        result = new Result<Phone>();
    }

    @Test
    public void successValidatePhone() {
        final Phone phone = new Phone("405", "205", "0999");
        phone.validate(result);
        assertTrue("Should have been valid", result.isSuccess());
    }

    private void verifyFailedNpa(final String npa) {
        final Phone phone = new Phone(npa, "205", "0999");
        phone.validate(result);
        assertFalse(SHOULD_HAVE_FAILED, result.isSuccess());
        assertTrue(phone.getNpa().validationStatusIs(ValidationStatus.invalidIllformed));
        assertTrue(phone.getNxx().validationStatusIs(ValidationStatus.valid));
        assertTrue(phone.getStation().validationStatusIs(ValidationStatus.valid));
        assertTrue(phone.getExtension().validationStatusIs(ValidationStatus.valid));
    }

    @Test
    public void failureInvalidNpa() {
        verifyFailedNxx("");
        verifyFailedNpa("109");
        verifyFailedNpa("290");
        verifyFailedNxx("aaa");
        verifyFailedNxx("3331");
    }

    private void verifyFailedNxx(final String nxx) {
        final Phone phone = new Phone("343", nxx, "0999");
        phone.validate(result);
        assertFalse(SHOULD_HAVE_FAILED, result.isSuccess());
        assertTrue(phone.getNpa().validationStatusIs(ValidationStatus.valid));
        assertTrue(phone.getNxx().validationStatusIs(ValidationStatus.invalidIllformed));
        assertTrue(phone.getStation().validationStatusIs(ValidationStatus.valid));
        assertTrue(phone.getExtension().validationStatusIs(ValidationStatus.valid));
    }

    @Test
    public void failureInvalidNxx() {
        verifyFailedNxx("");
        verifyFailedNxx("199");
        verifyFailedNxx("099");
        verifyFailedNxx("3331");
    }

    private void verifyFailedStation(final String station) {
        final Phone phone = new Phone("343", "333", station);
        phone.validate(result);
        assertFalse(SHOULD_HAVE_FAILED, result.isSuccess());
        assertTrue(phone.getNpa().validationStatusIs(ValidationStatus.valid));
        assertTrue(phone.getNxx().validationStatusIs(ValidationStatus.valid));
        assertTrue(phone.getStation().validationStatusIs(ValidationStatus.invalidIllformed));
        assertTrue(phone.getExtension().validationStatusIs(ValidationStatus.valid));
    }

    @Test
    public void failureInvalidStation() {
        verifyFailedStation("");
        verifyFailedStation("aaaa");
        verifyFailedStation("999a");
    }

    private void verifyFailedExtension(final String extension) {
        final Phone phone = new Phone("343", "333", "9393", extension);
        phone.validate(result);
        assertFalse(SHOULD_HAVE_FAILED, result.isSuccess());
        assertTrue(phone.getNpa().validationStatusIs(ValidationStatus.valid));
        assertTrue(phone.getNxx().validationStatusIs(ValidationStatus.valid));
        assertTrue(phone.getStation().validationStatusIs(ValidationStatus.valid));
        assertTrue(phone.getExtension().validationStatusIs(ValidationStatus.invalidIllformed));
    }

    @Test
    public void failureInvalidExtension() {
        verifyFailedExtension("a");
        verifyFailedExtension("999999999");
    }

}

package vehicle.type;

import vehicle.exception.SystemConfigurationException;
import vehicle.util.EqualsUtil;
import vehicle.validation.AbstractFieldValidator;
import vehicle.validation.NullValidator;

public abstract class AbstractField<T> implements Validateable<T> {
    private static final long serialVersionUID = 6975704936748809345L;
    private static final NullValidator DEFAULT_VALIDATOR = NullValidator.INSTANCE;

    private FieldChangedStatus changedStatus = FieldChangedStatus.unchanged;
    private ValidationStatus validationStatus = ValidationStatus.notValidated;

    private AbstractFieldValidator validator = DEFAULT_VALIDATOR;

    public AbstractField() {
        // Default constructor for construction with a null validator
    }

    @SuppressWarnings("unchecked")
    public AbstractField(final AbstractFieldValidator validator) {
        setValidator(validator);
    }

    protected abstract void setValueImpl(T value);

    public final void setValue(final T value) {
        setValueImpl(value);
        changedStatus = FieldChangedStatus.changed;
        validationStatus = ValidationStatus.notValidated;
    }

    @SuppressWarnings("unchecked")
    @Override
    public AbstractField<T> clone() {
        try {
            return (AbstractField<T>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new SystemConfigurationException("Some part of me is not cloneable", e);
        }
    }

    public abstract T getValue();

    @SuppressWarnings("unchecked")
    public void validate(final Result<?> result) {
        if (getValidator() != null) {
            getValidator().validate(this, result);
        } else {
            setValidationStatus(ValidationStatus.notValidated);
        }
    }

    public void setChangedStatus(final FieldChangedStatus changedStatus) {
        this.changedStatus = changedStatus;
    }

    public boolean isChanged() {
        return changedStatusIs(FieldChangedStatus.changed);
    }

    public void setValidationStatus(final ValidationStatus validationStatus) {
        this.validationStatus = validationStatus;
    }

    public FieldChangedStatus getChangedStatus() {
        return changedStatus;
    }

    public ValidationStatus getValidationStatus() {
        return validationStatus;
    }

    public boolean changedStatusIs(final FieldChangedStatus status) {
        return changedStatus.equals(status);
    }

    public boolean validationStatusIs(final ValidationStatus status) {
        return validationStatus.equals(status);
    }

    public boolean valueEquals(final T rhs) {
        return getValue().equals(rhs);
    }

    public boolean valueEquals(final AbstractField<T> rhs) {
        return getValue().equals(rhs.getValue());
    }

    public boolean isValid() {
        return getValidationStatus() == ValidationStatus.valid;
    }

    public boolean isValidOrNotYetValidated() {
        return getValidationStatus() != ValidationStatus.invalidIllformed
                && getValidationStatus() != ValidationStatus.invalidMissing
                && getValidationStatus() != ValidationStatus.invalidRange;
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }

    @Override
    public boolean equals(final Object rhs) {
        if (rhs != null && rhs.getClass() == getClass()) {
            final AbstractField field = (AbstractField) rhs;
            return EqualsUtil.equals(getValue(), field.getValue());
        }
        return false;
    }

    public final AbstractFieldValidator getValidator() {
        return validator;
    }

    public final void setValidator(final AbstractFieldValidator validator) {
        this.validator = validator;
    }

}

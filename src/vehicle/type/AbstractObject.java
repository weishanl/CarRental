package vehicle.type;

import vehicle.exception.SystemConfigurationException;

public abstract class AbstractObject<T> implements Validateable<T> {
    private ValidationStatus validationStatus = ValidationStatus.notValidated;

    public boolean isValid() {
        return getValidationStatus() == ValidationStatus.valid;
    }

    public final ValidationStatus getValidationStatus() {
        return validationStatus;
    }

    public final void setValidationStatus(final ValidationStatus validationStatus) {
        this.validationStatus = validationStatus;
    }

    @SuppressWarnings("unchecked")
    @Override
    public AbstractObject<T> clone() {
        try {
            return (AbstractObject<T>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new SystemConfigurationException(String.format("Unable to clone: %s", getClass()
                    .getName()), e);
        }
    }

}

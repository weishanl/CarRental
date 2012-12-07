package vehicle.type;

public interface Validateable<T> extends vehicle.domain.Cloneable<Validateable> {
    void validate(final Result<?> result);

    void setValidationStatus(final ValidationStatus status);

    boolean isValid();

    ValidationStatus getValidationStatus();
}

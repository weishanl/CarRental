package vehicle.domain;

import vehicle.type.AbstractObject;
import vehicle.type.Result;
import vehicle.type.StringField;
import vehicle.validation.StringRegexValidator;
import vehicle.validation.ValidationSupport;

public class VehicleLicense extends AbstractObject<VehicleLicense> {
    private static final long serialVersionUID = -4425631842100492597L;

    // Don't feel like validating all of the vehicle license formats
    private static final StringRegexValidator PLATE_VALIDATION = new StringRegexValidator(
            "^[\\w]{4,9}$");

    private StringField number;
    private IssuingState issuingState;

    public VehicleLicense(final String number, final IssuingState issuingState) {
        setNumber(number);
        setIssuingState(issuingState);
    }

    @Override
    public VehicleLicense clone() {
        return (VehicleLicense) super.clone();
    }

    public final IssuingState getIssuingState() {
        return issuingState;
    }

    public final void setIssuingState(final IssuingState issuingState) {
        this.issuingState = issuingState;
    }

    public final StringField getNumber() {
        return number;
    }

    public final void setNumber(final String number) {
        this.number = new StringField(number, PLATE_VALIDATION);
    }

    @Override
    public final boolean equals(final Object rhs) {
        if (rhs instanceof VehicleLicense) {
            final VehicleLicense license = (VehicleLicense) rhs;
            return number.valueEquals(number) && license.issuingState.equals(issuingState);
        }

        return false;

    }

    @Override
    public final int hashCode() {
        return number.hashCode() * issuingState.hashCode();
    }

    public void validate(final Result<?> result) {
        ValidationSupport.nullCheckAndValidate(IssuingState.class, issuingState, result);
        ValidationSupport.nullCheckAndValidate(StringField.class, number, result);
    }

}

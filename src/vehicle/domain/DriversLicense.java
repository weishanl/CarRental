package vehicle.domain;

import vehicle.type.AbstractObject;
import vehicle.type.Result;
import vehicle.type.StringField;
import vehicle.validation.DriversLicenseNumberValidator;
import vehicle.validation.ValidationSupport;

public class DriversLicense extends AbstractObject<DriversLicense> {
    private static final long serialVersionUID = -2220582988513802457L;
    private StringField number;
    private IssuingState issuingState;

    public DriversLicense(final String number, final IssuingState state) {
        setNumber(number);
        setIssuingState(state);
    }

    @Override
    public DriversLicense clone() {
        return (DriversLicense) super.clone();
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
        this.number = new StringField(number, DriversLicenseNumberValidator.getInstance());
    }

    public void validate(final Result<?> result) {
        ValidationSupport.nullCheckAndValidate(IssuingState.class, issuingState, result);
        DriversLicenseNumberValidator.getInstance().validate(this, result);
    }

    @Override
    public int hashCode() {
        return issuingState.hashCode() * number.hashCode();
    }

    public boolean equals(final Object rhs) {
        if (rhs instanceof DriversLicense) {
            final DriversLicense dl = (DriversLicense) rhs;
            return number.equals(dl.number) && issuingState.equals(dl.issuingState);
        }
        return false;
    }
}

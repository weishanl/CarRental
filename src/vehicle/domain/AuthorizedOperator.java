package vehicle.domain;

import vehicle.type.AbstractObject;
import vehicle.type.Result;
import vehicle.type.StringField;
import vehicle.validation.StringRegexValidator;
import vehicle.validation.ValidationSupport;

public class AuthorizedOperator extends AbstractObject<AuthorizedOperator> {
    private static final long serialVersionUID = 2954688794433813365L;

    private StringField name;
    private DriversLicense driversLicense;

    public AuthorizedOperator(final String name, final DriversLicense driversLicense) {
        setName(new StringField(name, NAME));
        setDriversLicense(driversLicense);
    }

    @Override
    public AuthorizedOperator clone() {
        return (AuthorizedOperator) super.clone();
    }

    public final DriversLicense getDriversLicense() {
        return driversLicense;
    }

    public final void setDriversLicense(final DriversLicense driversLicense) {
        this.driversLicense = driversLicense;
    }

    public final StringField getName() {
        return name;
    }

    public final void setName(final StringField name) {
        this.name = name;
    }

    public void validate(final Result<?> result) {
        ValidationSupport.nullCheckAndValidate(StringField.class, getName(), result);
        ValidationSupport.nullCheckAndValidate(DriversLicense.class, driversLicense, result);
    }

    private static final StringRegexValidator NAME = new StringRegexValidator("^[\\w. ]{1,40}$");
}

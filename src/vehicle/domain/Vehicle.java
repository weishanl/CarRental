package vehicle.domain;

import vehicle.type.AbstractObject;
import vehicle.type.Result;
import vehicle.type.StringField;
import vehicle.validation.StringRegexValidator;
import vehicle.validation.ValidationSupport;

public class Vehicle extends AbstractObject<Vehicle> {
    private static final long serialVersionUID = 8069948307102400661L;

    private static final StringRegexValidator REQUIRED_20 = new StringRegexValidator("^[\\w]{1,20}");

    private StringField color;
    private VehicleType type;
    private VehicleLicense license;

    public Vehicle(final VehicleType type, final String color, final VehicleLicense license) {
        setColor(color);
        setType(type);
        setLicense(license);
    }

    public Vehicle clone() {
        return (Vehicle) super.clone();
    }

    public final StringField getColor() {
        return color;
    }

    public final void setColor(final String color) {
        this.color = new StringField(color, REQUIRED_20);
    }

    public final VehicleLicense getLicense() {
        return license;
    }

    public final void setLicense(final VehicleLicense license) {
        this.license = license;
    }

    public final VehicleType getType() {
        return type;
    }

    public final void setType(final VehicleType type) {
        this.type = type;
    }

    public void validate(final Result<?> result) {
        ValidationSupport.nullCheckAndValidate(StringField.class, color, result);
        ValidationSupport.nullCheckAndValidate(VehicleLicense.class, license, result);
        ValidationSupport.nullCheckAndValidate(VehicleType.class, type, result);
    }
}

package vehicle.domain;

import vehicle.type.AbstractObject;
import vehicle.type.IntegerField;
import vehicle.type.Result;
import vehicle.type.StringField;
import vehicle.validation.IntegerGreaterThanValidator;
import vehicle.validation.StringRegexValidator;
import vehicle.validation.ValidationSupport;

public final class VehicleType extends AbstractObject<VehicleType> {
    private static final long serialVersionUID = -1788607937162895414L;
    private StringField name;
    private StringField make;
    private StringField model;
    private IntegerField year;
    private ValidState state = ValidState.valid;

    @Override
    public String toString() {
        return "{" + name.getValue() + ", " + state + "}";
    }

    public VehicleType(final String name, final String make, final String model, final int year) {
        setName(name);
        setMake(make);
        setModel(model);
        setYear(year);
    }

    public VehicleType(final String name, final String make, final String model, final int year,
            final ValidState state) {
        setName(name);
        setMake(make);
        setModel(model);
        setYear(year);
        setState(state);
    }

    @Override
    public VehicleType clone() {
        final VehicleType type = (VehicleType) super.clone();
        type.name = name.clone();
        return type;
    }

    public StringField getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = new StringField(name, NAME_UPTO_20);
    }

    public ValidState getState() {
        return state;
    }

    public void setState(final ValidState state) {
        this.state = state;
    }

    @Override
    public int hashCode() {
        return name.hashCode() * state.hashCode();
    }

    @Override
    public boolean equals(final Object rhs) {
        if (rhs instanceof VehicleType) {
            final VehicleType vt = (VehicleType) rhs;
            return name.getValue().equals(vt.getName().getValue());
        }
        return false;
    }

    public void validate(final Result<?> result) {
        ValidationSupport.nullCheckAndValidate(StringField.class, name, result);
        ValidationSupport.nullCheckAndValidate(StringField.class, make, result);
        ValidationSupport.nullCheckAndValidate(StringField.class, model, result);
        ValidationSupport.nullCheckAndValidate(IntegerField.class, year, result);
    }

    public StringField getMake() {
        return make;
    }

    public void setMake(final String make) {
        this.make = new StringField(make, REQUIRED_20);
    }

    public StringField getModel() {
        return model;
    }

    public void setModel(final String model) {
        this.model = new StringField(model, REQUIRED_20);
    }

    public IntegerField getYear() {
        return year;
    }

    public void setYear(final int year) {
        this.year = new IntegerField(year, AFTER_1975);
    }

    private static final StringRegexValidator NAME_UPTO_20 = new StringRegexValidator(
            "^[\\w ]{1,20}$");
    private static final StringRegexValidator REQUIRED_20 = new StringRegexValidator("^[\\w]{1,20}");
    private static final IntegerGreaterThanValidator AFTER_1975 = new IntegerGreaterThanValidator(
            1975);
}

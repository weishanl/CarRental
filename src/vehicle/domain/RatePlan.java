package vehicle.domain;

import vehicle.type.AbstractObject;
import vehicle.type.Result;
import vehicle.type.StringField;
import vehicle.validation.StringRegexValidator;
import vehicle.validation.ValidationSupport;

public class RatePlan extends AbstractObject<RatePlan> {
    private static final long serialVersionUID = -526740708582978410L;

    private static final StringRegexValidator REQUIRED_UPTO_20 = new StringRegexValidator(
            "^[\\w ]{1,20}$");

    private StringField name = new StringField("", REQUIRED_UPTO_20);
    private RateSchedule rateSchedule;
    private ValidState valid = ValidState.valid;
    private VehicleType vehicleType;

    public RatePlan(final String name, final RateSchedule schedule, final VehicleType type,
            final ValidState state) {
        setName(name);
        setRateSchedule(schedule);
        this.valid = state;
        setVehicleType(type);
    }

    @Override
    public RatePlan clone() {
        final RatePlan rp = (RatePlan) super.clone();
        rp.name = name.clone();
        rp.vehicleType = vehicleType.clone();
        return rp;
    }

    public final StringField getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = new StringField(name, REQUIRED_UPTO_20);
    }

    public final VehicleType getVehicleType() {
        return vehicleType;
    }

    public final void setVehicleType(final VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void validate(final Result<?> result) {
        ValidationSupport.nullCheckAndValidate(RateSchedule.class, rateSchedule, result);
        ValidationSupport.nullCheckAndValidate(VehicleType.class, vehicleType, result);
        ValidationSupport.nullCheckAndValidate(StringField.class, name, result);
    }

    public final ValidState getValid() {
        return valid;
    }

    public final void setValid(final ValidState valid) {
        this.valid = valid;
    }

    public final RateSchedule getRateSchedule() {
        return rateSchedule;
    }

    public final void setRateSchedule(final RateSchedule rateSchedule) {
        this.rateSchedule = rateSchedule;
    }
}

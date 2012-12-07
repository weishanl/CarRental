package vehicle.domain;

import vehicle.type.AbstractObject;
import vehicle.type.FloatField;
import vehicle.type.Result;
import vehicle.validation.FloatIsGreaterThanOrEqualToZero;
import vehicle.validation.ValidationSupport;

public class RateSchedule extends AbstractObject<RateSchedule> {
    private static final long serialVersionUID = 5687431611575982164L;
    private FloatField weeklyRate;
    private FloatField dailyRate;
    private FloatField authorizedDriver;
    private FloatField additionalDay;
    private FloatField additionalHour;

    public RateSchedule() {
        weeklyRate = new FloatField(-1, FloatIsGreaterThanOrEqualToZero.INSTANCE);
        dailyRate = new FloatField(-1, FloatIsGreaterThanOrEqualToZero.INSTANCE);
        authorizedDriver = new FloatField(-1, FloatIsGreaterThanOrEqualToZero.INSTANCE);
        additionalDay = new FloatField(-1, FloatIsGreaterThanOrEqualToZero.INSTANCE);
        additionalHour = new FloatField(-1, FloatIsGreaterThanOrEqualToZero.INSTANCE);
    }

    public RateSchedule(final float dailyRate, final float weeklyRate,
            final float authorizedDriver, final float additionalDay, final float additionalHour) {
        this.weeklyRate = new FloatField(weeklyRate, FloatIsGreaterThanOrEqualToZero.INSTANCE);
        this.dailyRate = new FloatField(dailyRate, FloatIsGreaterThanOrEqualToZero.INSTANCE);
        this.authorizedDriver = new FloatField(authorizedDriver,
                FloatIsGreaterThanOrEqualToZero.INSTANCE);
        this.additionalDay = new FloatField(additionalDay, FloatIsGreaterThanOrEqualToZero.INSTANCE);
        this.additionalHour = new FloatField(additionalHour,
                FloatIsGreaterThanOrEqualToZero.INSTANCE);
    }

    public final FloatField getAdditionalDay() {
        return additionalDay;
    }

    public final void setAdditionalDay(final FloatField additionalDay) {
        this.additionalDay = additionalDay;
    }

    public final FloatField getAdditionalHour() {
        return additionalHour;
    }

    public final void setAdditionalHour(final FloatField additionalHour) {
        this.additionalHour = additionalHour;
    }

    public final FloatField getAuthorizedDriver() {
        return authorizedDriver;
    }

    public final void setAuthorizedDriver(final FloatField authorizedDriver) {
        this.authorizedDriver = authorizedDriver;
    }

    public final FloatField getDailyRate() {
        return dailyRate;
    }

    public final void setDailyRate(final FloatField dailyRate) {
        this.dailyRate = dailyRate;
    }

    public final FloatField getWeeklyRate() {
        return weeklyRate;
    }

    public final void setWeeklyRate(final FloatField weeklyRate) {
        this.weeklyRate = weeklyRate;
    }

    @Override
    public RateSchedule clone() {
        final RateSchedule clone = (RateSchedule) super.clone();
        clone.additionalDay = additionalDay.clone();
        clone.additionalHour = additionalHour.clone();
        clone.authorizedDriver = authorizedDriver.clone();
        clone.dailyRate = dailyRate.clone();
        clone.weeklyRate = weeklyRate.clone();
        return clone;
    }

    public void validate(final Result<?> result) {
        ValidationSupport.nullCheckAndValidate(FloatField.class, weeklyRate, result);
        ValidationSupport.nullCheckAndValidate(FloatField.class, dailyRate, result);
        ValidationSupport.nullCheckAndValidate(FloatField.class, additionalDay, result);
        ValidationSupport.nullCheckAndValidate(FloatField.class, additionalHour, result);
        ValidationSupport.nullCheckAndValidate(FloatField.class, authorizedDriver, result);
    }

}

package vehicle.domain;

import vehicle.type.AbstractObject;
import vehicle.type.Result;
import vehicle.type.StringField;
import vehicle.validation.AbstractFieldValidator;
import vehicle.validation.StringRegexValidator;
import vehicle.validation.ValidationSupport;

public class Phone extends AbstractObject<Phone> {
    private static final long serialVersionUID = 8615249351151754537L;

    private static final AbstractFieldValidator<StringField> VALIDATE_NPA = new StringRegexValidator(
            "^[2-9][0-8][0-9]$");
    private static final AbstractFieldValidator<StringField> VALIDATE_NXX = new StringRegexValidator(
            "^[2-9][0-9][0-9]$");
    private static final AbstractFieldValidator<StringField> VALIDATE_STATION = new StringRegexValidator(
            "^[0-9]{4}?");
    private static final AbstractFieldValidator<StringField> VALIDATE_EXTENSION = new StringRegexValidator(
            "^[0-9]{0,8}?");

    private PhoneType type;
    private StringField npa;
    private StringField nxx;
    private StringField station;
    private StringField extension;

    public Phone() {
        this("", "", "", "", PhoneType.primary);
    }

    public Phone(final String npa, final String nxx, final String station) {
        this(npa, nxx, station, "", PhoneType.primary);
    }

    public Phone(final String npa, final String nxx, final String station, final String extension) {
        this(npa, nxx, station, extension, PhoneType.primary);
    }

    public Phone(final String npa, final String nxx, final String station, final String extension,
            final PhoneType type) {
        setNpa(new StringField(npa, VALIDATE_NPA));
        setNxx(new StringField(nxx, VALIDATE_NXX));
        setStation(new StringField(station, VALIDATE_STATION));
        setExtension(new StringField(extension, VALIDATE_EXTENSION));
        setType(type);
    }

    @Override
    public Phone clone() {
        final Phone clone = (Phone) super.clone();
        clone.npa = npa.clone();
        clone.nxx = nxx.clone();
        clone.station = station.clone();
        clone.extension = extension.clone();
        return clone;
    }

    public final PhoneType getType() {
        return type;
    }

    public final void setType(final PhoneType type) {
        this.type = type;
    }

    public final StringField getExtension() {
        return extension;
    }

    public final void setExtension(final StringField extension) {
        this.extension = extension;
    }

    public final StringField getNpa() {
        return npa;
    }

    public final void setNpa(final StringField npa) {
        this.npa = npa;
    }

    public final StringField getNxx() {
        return nxx;
    }

    public final void setNxx(final StringField nxx) {
        this.nxx = nxx;
    }

    public final StringField getStation() {
        return station;
    }

    public final void setStation(final StringField station) {
        this.station = station;
    }

    public void validate(final Result<?> result) {
        ValidationSupport.nullCheckAndValidate(StringField.class, getNpa(), result);
        ValidationSupport.nullCheckAndValidate(StringField.class, getNxx(), result);
        ValidationSupport.nullCheckAndValidate(StringField.class, getStation(), result);
        ValidationSupport.nullCheckAndValidate(StringField.class, getExtension(), result);
    }

}

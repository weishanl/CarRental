package vehicle.domain;

import java.util.ArrayList;
import java.util.List;

import vehicle.type.AbstractObject;
import vehicle.type.ListField;
import vehicle.type.Result;
import vehicle.type.StringField;
import vehicle.validation.MultiplicityValidator;
import vehicle.validation.StringRegexValidator;
import vehicle.validation.ValidationSupport;

public class Address extends AbstractObject<Address> {
    private static final long serialVersionUID = -8420931417983526050L;

    private static final StringRegexValidator REQUIRE_40 = new StringRegexValidator(
            "^[\\w ]{1,40}$");
    private static final StringRegexValidator OPTIONAL_40 = new StringRegexValidator(
            "^[\\w ]{0,40}$");
    private static final StringRegexValidator ZIP = new StringRegexValidator("^[0-9]{5}$");

    private StringField line1;
    private StringField line2;
    private StringField city;
    private StringField state;
    private StringField zip;
    private AddressType type;
    private ListField<Phone> phones;

    public Address(final String line1, final String line2, final String city, final String state, // NOPMD
            final String zip, final AddressType type, final List<Phone> phones) {
        setLine1(line1);
        setLine2(line2);
        setCity(city);
        setState(state);
        setZip(zip);
        setType(type);
        setPhones(new ListField<Phone>(new ArrayList<Phone>(phones.size()),
                MultiplicityValidator.ONE_TO_MANY));
        getPhones().addAll(phones);
    }

    public Address(final String line1, final String line2, final String city, final String state, // NOPMD
            final String zip, final AddressType type, final Phone phone) {
        setLine1(line1);
        setLine2(line2);
        setCity(city);
        setState(state);
        setZip(zip);
        setType(type);
        setPhones(new ListField<Phone>(new ArrayList<Phone>(2), MultiplicityValidator.ONE_TO_MANY));
        addPhone(phone);
    }

    @Override
    public Address clone() {
        return (Address) super.clone();
    }

    public final StringField getCity() {
        return city;
    }

    public final void setCity(final String city) {
        this.city = new StringField(city, REQUIRE_40);
    }

    public final StringField getLine1() {
        return line1;
    }

    public final void setLine1(final String line1) {
        this.line1 = new StringField(line1, REQUIRE_40);
    }

    public final StringField getLine2() {
        return line2;
    }

    public final void setLine2(final String line2) {
        this.line2 = new StringField(line2, OPTIONAL_40);
    }

    public final ListField<Phone> getPhones() {
        return phones;
    }

    public final void setPhones(final ListField<Phone> phones) {
        this.phones = phones;
    }

    public final StringField getState() {
        return state;
    }

    public final void setState(final String state) {
        this.state = new StringField(state, REQUIRE_40);
    }

    public final AddressType getType() {
        return type;
    }

    public final void setType(final AddressType type) {
        this.type = type;
    }

    public final StringField getZip() {
        return zip;
    }

    public final void setZip(final String zip) {
        this.zip = new StringField(zip, ZIP);
    }

    public final void addPhone(final Phone phone) {
        phones.add(phone);
    }

    public void validate(final Result<?> result) {
        ValidationSupport.nullCheckAndValidate(StringField.class, getLine1(), result);
        ValidationSupport.nullCheckAndValidate(StringField.class, getLine2(), result);
        ValidationSupport.nullCheckAndValidate(StringField.class, getCity(), result);
        ValidationSupport.nullCheckAndValidate(StringField.class, getCity(), result);
        ValidationSupport.nullCheckAndValidate(StringField.class, getZip(), result);
        ValidationSupport.nullCheckAndValidate(StringField.class, getState(), result);
        ValidationSupport.nullCheckAndValidate(ListField.class, getPhones(), result);

        for (Phone phone : getPhones().getValue()) {
            phone.validate(result);
        }
    }

}

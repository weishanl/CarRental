package vehicle.domain;

import java.util.ArrayList;
import java.util.Iterator;

import vehicle.type.ListField;
import vehicle.type.Result;
import vehicle.validation.MultiplicityValidator;
import vehicle.validation.ValidationSupport;

public class Renter extends AuthorizedOperator {
    private static final long serialVersionUID = -1053396924967172354L;

    private ListField<Address> addresses;

    public Renter(final String name, final Address address, final DriversLicense driversLicense) {
        super(name, driversLicense);
        setAddresses(new ListField<Address>(new ArrayList<Address>(2), VALIDATOR));
        addAddress(address);
    }

    @Override
    public Renter clone() {
        return (Renter) super.clone();
    }

    public Iterator<Address> addressIterator() {
        return getAddresses().iterator();
    }

    public final void addAddress(final Address address) {
        getAddresses().add(address);
    }

    public void removeAddress(final Address address) {
        getAddresses().remove(address);
    }

    private ListField<Address> getAddresses() {
        return addresses;
    }

    private void setAddresses(final ListField<Address> addresses) {
        this.addresses = addresses;
    }

    public void validate(final Result<?> result) {
        super.validate(result);
        ValidationSupport.nullCheckAndValidate(ListField.class, getAddresses(), result);
        for (Address address : getAddresses().getValue()) {
            address.validate(result);
        }
    }

    private static final MultiplicityValidator VALIDATOR = new MultiplicityValidator(1, 2);
}

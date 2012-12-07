package vehicle.domain;

import java.util.ArrayList;

import vehicle.type.AbstractObject;
import vehicle.type.ListField;
import vehicle.type.Result;
import vehicle.validation.MultiplicityValidator;
import vehicle.validation.ValidationSupport;

public class RentalAgreement extends AbstractObject<RentalAgreement> {
    public static final MultiplicityValidator OPERATOR_COUNT = new MultiplicityValidator(0, 3);

    private static final long serialVersionUID = 3673105479193761463L;
    public static final int DEFAULT_AUTH_OP_SIZE = 4;
    private Long id;
    private Renter renter;
    private ListField<AuthorizedOperator> authorizedOperators;
    private Vehicle vehicle;
    private RatePlan rate;

    public RentalAgreement() {
        setAuthorizedOperators(new ListField<AuthorizedOperator>(new ArrayList<AuthorizedOperator>(
                DEFAULT_AUTH_OP_SIZE), OPERATOR_COUNT));
    }

    public RentalAgreement(final Renter renter, final Vehicle vehicle, final RatePlan rate) {
        this();
        setVehicle(vehicle);
        setRate(rate);
        setRenter(renter);
    }

    @Override
    public RentalAgreement clone() {
        return (RentalAgreement) super.clone();
    }

    public final void addAuthorizedDriver(final AuthorizedOperator person) {
        for (AuthorizedOperator current : authorizedOperators.getValue()) {
            if (matches(current, person)) {
                return;
            }
        }
        if (matches(renter, person)) {
            return;
        }
        authorizedOperators.add(person);
    }

    private boolean matches(final AuthorizedOperator lhs, final AuthorizedOperator rhs) {
        return lhs.getDriversLicense().equals(rhs.getDriversLicense())
                && lhs.getName().valueEquals(rhs.getName());

    }

    public final RatePlan getRate() {
        return rate;
    }

    public final void setRate(final RatePlan rate) {
        this.rate = rate;
    }

    public final Vehicle getVehicle() {
        return vehicle;
    }

    public final void setVehicle(final Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public final ListField<AuthorizedOperator> getAuthorizedOperators() {
        return authorizedOperators;
    }

    public final void setAuthorizedOperators(final ListField<AuthorizedOperator> authorizedOperators) {
        this.authorizedOperators = authorizedOperators;
    }

    public final Renter getRenter() {
        return renter;
    }

    public final void setRenter(final Renter renter) {
        this.renter = renter;
    }

    public void validate(final Result<?> result) {
        ValidationSupport.nullCheckAndValidate(Renter.class, renter, result);
        ValidationSupport.nullCheckAndValidate(Vehicle.class, vehicle, result);
        ValidationSupport.nullCheckAndValidate(RatePlan.class, rate, result);
        ValidationSupport.nullCheckAndValidate(ListField.class, authorizedOperators, result);
        for (AuthorizedOperator operator : authorizedOperators.getValue()) {
            ValidationSupport.nullCheckAndValidate(AuthorizedOperator.class, operator, result);
        }
    }

    public final Long getId() {
        return id;
    }

    public final void setId(final Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        if (getId() == null) {
            return 0;
        } else {
            return getId().hashCode();
        }
    }

    @Override
    public boolean equals(final Object rhs) {
        if (rhs instanceof RentalAgreement) {
            final Long rhsKey = ((RentalAgreement) rhs).getId();
            if (getId() == null || rhsKey == null) {
                return false;
            }
            return getId().equals(rhsKey);
        }

        return false;
    }
}

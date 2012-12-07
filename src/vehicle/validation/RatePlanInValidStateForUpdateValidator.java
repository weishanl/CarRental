package vehicle.validation;

import vehicle.domain.RatePlan;
import vehicle.domain.ValidState;
import vehicle.exception.ObjectInIncorrectState;
import vehicle.type.Result;
import vehicle.type.ValidationStatus;

public class RatePlanInValidStateForUpdateValidator extends AbstractObjectValidator<RatePlan> {
    @Override
    public void validateImpl(final RatePlan ratePlan, final Result<?> result) {

        ratePlan.setValidationStatus(ValidationStatus.valid);

        if (ratePlan.getValid() != ValidState.valid) {
            result.setSuccess(false);
            ratePlan.setValidationStatus(ValidationStatus.invalidIllformed);
            throw new ObjectInIncorrectState(RatePlan.class, ratePlan.getName().getValue()
                    + ratePlan.getVehicleType().getName().getValue(), ratePlan.getValid(),
                    ValidState.valid);
        }
    }

}

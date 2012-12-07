package vehicle.component.rateplan;

import vehicle.domain.RatePlan;
import vehicle.domain.ValidState;
import vehicle.domain.VehicleType;
import vehicle.integration.IRatePlanDao;
import vehicle.type.Result;
import vehicle.validation.AbstractObjectValidator;
import vehicle.validation.ValidationSupport;

public class RatePlanComponent {
    private IRatePlanDao dao;
    private AbstractObjectValidator<RatePlan> ratePlanInValidStateForUpdateValidator;

    public Result<RatePlan> removeRatePlan(final String name, final VehicleType vehicleType) {
        final Result<RatePlan> result = new Result<RatePlan>(null, true);
        dao.removeRatePlan(name, vehicleType);
        return result;
    }

    public Result<RatePlan> createRatePlan(final RatePlan ratePlan) {
        final Result<RatePlan> result = new Result<RatePlan>(ratePlan, true);

        ratePlan.validate(result);

        if (result.isSuccess()) {
            dao.createRatePlan(ratePlan);
        }

        return result;
    }

    public Result<RatePlan> retrieveRatePlan(final String name, final String vehicleTypeName) {
        final RatePlan ratePlan = dao.retrieveRatePlan(name, vehicleTypeName);
        return new Result<RatePlan>(ratePlan, true);
    }
    
    public Result<RatePlan> invalidateRatePlan(final RatePlan ratePlan) {
        final Result<RatePlan> result = new Result<RatePlan>(ratePlan, true);
        
        ratePlan.validate(result);
        
        if(result.isSuccess()) {
            ratePlan.setValid(ValidState.invalid);
            dao.updateRatePlan(ratePlan);
        }
        
        return result;
    }

    public Result<RatePlan> updateRatePlan(final RatePlan ratePlan) {
        final Result<RatePlan> result = new Result<RatePlan>(ratePlan, true);

        ratePlan.validate(result);
        ValidationSupport.validateIfSuccess(ratePlan, result,
                ratePlanInValidStateForUpdateValidator);

        if (result.isSuccess()) {
            dao.updateRatePlan(ratePlan);
        }

        return result;
    }

    public void removeAll() {
        dao.removeAll();
    }

    public final IRatePlanDao getDao() {
        return dao;
    }

    public final void setDao(final IRatePlanDao dao) {
        this.dao = dao;
    }

    public final AbstractObjectValidator<RatePlan> getRatePlanInValidStateForUpdateValidator() {
        return ratePlanInValidStateForUpdateValidator;
    }

    public final void setRatePlanInValidStateForUpdateValidator(
            final AbstractObjectValidator<RatePlan> ratePlanInValidStateForUpdateValidator) {
        this.ratePlanInValidStateForUpdateValidator = ratePlanInValidStateForUpdateValidator;
    }
}

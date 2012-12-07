package vehicle.component.rentalagreement;

import vehicle.domain.RentalAgreement;
import vehicle.integration.IRentalAgreementDao;
import vehicle.type.Result;
import vehicle.validation.ValidationSupport;

public class RentalAgreementComponent {
    private IRentalAgreementDao dao;

    public Result<RentalAgreement> createRentalAgreement(final RentalAgreement ra1) {
        final Result<RentalAgreement> result = new Result<RentalAgreement>(ra1, true);

        ValidationSupport.nullCheckAndValidate(RentalAgreement.class, ra1, result);

        if (result.isSuccess()) {
            dao.createRentalAgreement(ra1);
        }

        return result;
    }

    public Result<RentalAgreement> removeRentalAgreement(final Long key) {
        dao.removeRentalAgreement(key);
        return new Result<RentalAgreement>();
    }

    public final IRentalAgreementDao getDao() {
        return dao;
    }

    public final void setDao(final IRentalAgreementDao dao) {
        this.dao = dao;
    }

}

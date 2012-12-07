package vehicle.validation;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import vehicle.domain.DriversLicense;
import vehicle.exception.SystemConfigurationException;
import vehicle.reference.IsoStateCodes;
import vehicle.type.ValidationStatus;
import vehicle.type.Result;
import vehicle.type.StringField;

public class DriversLicenseNumberValidator extends AbstractFieldValidator<StringField> {
    private final Map<String, StringRegexValidator> stateToValidation = new HashMap<String, StringRegexValidator>(
            101);

    private static final DriversLicenseNumberValidator INSTANCE = new DriversLicenseNumberValidator();

    public static DriversLicenseNumberValidator getInstance() {
        return INSTANCE;
    }

    public DriversLicenseNumberValidator() {
        init();
    }

    @Override
    protected void validateChangedAndNotYetValidated(final StringField field, final Result<?> result) {
        // TODO Auto-generated method stub

    }

    public void validate(final DriversLicense license, final Result<?> result) {
        final StringRegexValidator validator = stateToValidation.get(license.getIssuingState()
                .getName().getValue());

        if (validator == null) {
            throw new SystemConfigurationException("Validator not found for state: "
                    + license.getNumber().getValue());
        }

        license.getNumber().setValidationStatus(ValidationStatus.valid);
        validator.validate(license.getNumber(), result);
    }

    private void init() {
        try {
            final Properties properties = new Properties();
            final InputStream fis = ClassLoader.getSystemResourceAsStream(getClass().getName()
                    .replaceAll("\\.", "/")
                    + ".properties");
            properties.load(fis);
            fis.close();
            final Iterator<Object> iter = properties.keySet().iterator();
            while (iter.hasNext()) {
                final String key = (String) iter.next();
                register((String) key, properties.getProperty(key));
            }
        } catch (IOException e) {
            throw new SystemConfigurationException("Unable to load file", e);
        }
    }

    private void register(final String stateCode, final String regex) {
        if (!IsoStateCodes.INSTANCE.isValidCode(stateCode)) {
            throw new SystemConfigurationException("Invalid state code: " + stateCode);
        }
        this.stateToValidation.put(stateCode, new StringRegexValidator(regex));
    }

}

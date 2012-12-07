package vehicle.reference;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import vehicle.exception.SystemConfigurationException;

public class IsoStateCodes {
    private final Map<String, String> codeToName = new Hashtable<String, String>(101);
    private final Map<String, String> nameToCode = new Hashtable<String, String>(101);

    public static final IsoStateCodes INSTANCE = new IsoStateCodes();

    public IsoStateCodes() {
        init();
    }

    public String convertCodeToName(final String code) {
        return codeToName.get(code);
    }

    public String convertNameToCode(final String name) {
        return nameToCode.get(name);
    }

    public boolean isValidCode(final String stateCode) {
        return codeToName.containsKey(stateCode);
    }

    public boolean isValidName(final String name) {
        return nameToCode.containsKey(name);
    }

    private void put(final String code, final String name) {
        codeToName.put(code, name);
        nameToCode.put(name, code);
    }

    private void init() {
        try {
            final Properties props = new Properties();
            final InputStream fis = ClassLoader.getSystemResourceAsStream(getClass().getName()
                    .replaceAll("\\.", "/")
                    + ".properties");
            props.load(fis);
            fis.close();
            final Iterator<Object> iter = props.keySet().iterator();
            while (iter.hasNext()) {
                final String code = (String) iter.next();
                put(code, props.getProperty(code));
            }
        } catch (IOException e) {
            throw new SystemConfigurationException("Cannot open IsoStateCode.properties", e);
        }
    }
}

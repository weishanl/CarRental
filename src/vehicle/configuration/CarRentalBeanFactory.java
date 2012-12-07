package vehicle.configuration;

import loggingutil.LoggingConfiguration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import vehicle.exception.SystemConfigurationException;

public final class CarRentalBeanFactory {
    private CarRentalBeanFactory() {
        // I'm a util class
    }

    private static ApplicationContext context = null;

    static {
        LoggingConfiguration.initialize();
        try {
            context = new ClassPathXmlApplicationContext("vehicle/configuration/beans.xml");
        } catch (Exception e) {
            // Note, I use Object.class instead of CarRentalBeanFactory.class
            // since this is a static initializer and I'm not yet initialized.
            // It also avoids a possible circular reference between this class
            // and the LoggerConfiguration class.
            LoggingConfiguration.getLoggerFor(Object.class).fatal(e,
                    "Unable to find beans.xml for Spring Configuration");
        }
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static <C> C getBean(final Class<C> clazz) {
        return getBean(clazz, componentNameFromClass(clazz));
    }

    @SuppressWarnings("unchecked")
    public static <C> C getBean(final Class<C> clazz, final String name) {
        Object result = getContext().getBean(name);
        if (clazz.isAssignableFrom(result.getClass())) {
            return (C) result;
        }
        throw new SystemConfigurationException(String.format(
                "Found bean of wrong class. Expected: %s, Found: %s", clazz, result.getClass()));
    }

    private static String componentNameFromClass(final Class clazz) {
        final String name = clazz.getName();
        int indexOfName = name.lastIndexOf('.');
        if (indexOfName < 0) {
            indexOfName = 0;
        } else {
            ++indexOfName;
        }

        if (clazz.isInterface()) {
            ++indexOfName;
        }
        return Character.toLowerCase(name.charAt(indexOfName)) + name.substring(indexOfName + 1);
    }

}

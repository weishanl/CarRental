package vehicle.exception;

public class SystemConfigurationException extends RuntimeException {
    private static final long serialVersionUID = 3164052229274691143L;

    public SystemConfigurationException(final String message) {
        super(message);
    }

    public SystemConfigurationException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

}

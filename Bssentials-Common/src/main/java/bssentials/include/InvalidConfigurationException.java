package bssentials.include;

/**
 * Exception thrown when attempting to load an invalid {@link Configuration}
 */
@SuppressWarnings("serial")
public class InvalidConfigurationException extends Exception {

    public InvalidConfigurationException() {}

    public InvalidConfigurationException(String msg) {
        super(msg);
    }

    public InvalidConfigurationException(Throwable cause) {
        super(cause);
    }

    public InvalidConfigurationException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
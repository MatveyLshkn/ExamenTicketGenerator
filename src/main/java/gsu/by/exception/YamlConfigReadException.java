package gsu.by.exception;

public class YamlConfigReadException extends RuntimeException {

    public YamlConfigReadException() {
        super();
    }

    public YamlConfigReadException(String message) {
        super(message);
    }

    public YamlConfigReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public YamlConfigReadException(Throwable cause) {
        super(cause);
    }
}

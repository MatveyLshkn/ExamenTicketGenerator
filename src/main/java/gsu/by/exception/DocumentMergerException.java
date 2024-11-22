package gsu.by.exception;

public class DocumentMergerException extends RuntimeException {
    public DocumentMergerException() {
        super();
    }

    public DocumentMergerException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentMergerException(Throwable cause) {
        super(cause);
    }

    public DocumentMergerException(String message) {
        super(message);
    }
}

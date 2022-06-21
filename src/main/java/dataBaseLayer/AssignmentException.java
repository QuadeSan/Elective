package dataBaseLayer;

public class AssignmentException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AssignmentException(String message) {
        super(message);
    }

    public AssignmentException(Throwable cause) {
        super(cause);
    }

    public AssignmentException(String message, Throwable cause) {
        super(message, cause);
    }

}

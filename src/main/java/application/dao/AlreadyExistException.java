package application.dao;

public class AlreadyExistException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public AlreadyExistException(String message) {
        super(message);
    }

    public AlreadyExistException(Throwable cause) {
        super(cause);
    }

    public AlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

}

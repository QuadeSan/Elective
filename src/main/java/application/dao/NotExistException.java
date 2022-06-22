package application.dao;

public class NotExistException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public NotExistException(String message) {
        super(message);
    }

    public NotExistException(Throwable cause) {
        super(cause);
    }

    public NotExistException(String message, Throwable cause) {
        super(message, cause);
    }

}

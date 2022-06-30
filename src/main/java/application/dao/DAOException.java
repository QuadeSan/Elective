package application.dao;

/**
 * Special exception thrown when something goes wrong
 * on data layer and no one know how to handle specific exception
 */
public class DAOException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public DAOException(String message) {
        super(message);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}

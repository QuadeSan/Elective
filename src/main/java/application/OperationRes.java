package application;

public class OperationRes<T> extends application.OperationResult {

    T result;

    public OperationRes(boolean success, String message, T result) {
        super(success, message);
        this.result = result;
    }

    public T getResult() {
        return result;
    }
}

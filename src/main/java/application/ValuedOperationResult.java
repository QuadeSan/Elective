package application;

import java.util.Objects;

public class ValuedOperationResult<T> extends application.OperationResult {

    T result;

    public ValuedOperationResult(boolean success, String message, T result) {
        super(success, message);
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ValuedOperationResult<?> that = (ValuedOperationResult<?>) o;
        return Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), result);
    }
}

package atm.infrastructure.exception;

public class UnacceptableInputAmountException extends RuntimeException {

    public UnacceptableInputAmountException(String message) {
        super(message);
    }

}

package atm.infrastructure.exception;

public class InsufficientBanknoteAmountException extends RuntimeException {

    public InsufficientBanknoteAmountException(String message) {
        super(message);
    }

}

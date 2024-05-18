package sae.scanmedback.errors;

public class EmailAlreadyUsedException extends Exception {
    public EmailAlreadyUsedException(String errorMessage) {
        super(errorMessage);
    }
}

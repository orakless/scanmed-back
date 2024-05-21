package sae.scanmedback.errors;

public class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String errorMessage) {
        super(errorMessage);
    }
}

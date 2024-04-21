package sae.scanmedback.errors;

public class AppareilAlreadyAuthenticatedException extends Exception {
    public AppareilAlreadyAuthenticatedException(String errorMessage) {
        super(errorMessage);
    }
}

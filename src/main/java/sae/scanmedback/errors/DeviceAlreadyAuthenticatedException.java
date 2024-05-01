package sae.scanmedback.errors;

public class DeviceAlreadyAuthenticatedException extends Exception {
    public DeviceAlreadyAuthenticatedException(String errorMessage) {
        super(errorMessage);
    }
}

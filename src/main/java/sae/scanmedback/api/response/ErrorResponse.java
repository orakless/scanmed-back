package sae.scanmedback.api.response;

public class ErrorResponse implements IResponse {

    private final String status;

    private final String message;
    public ErrorResponse(String errorMessage) {
        String[] parsedError = errorMessage.split(";");
        this.status = parsedError[0];
        this.message = parsedError[1];
    }

    public String getStatus() {
        return status;
    }
    public Object getData() {
        return null;
    }
    public String getMessage() {
        return message;
    }
}

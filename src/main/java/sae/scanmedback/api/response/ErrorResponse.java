package sae.scanmedback.api.response;

public class ErrorResponse {

    private String errorCode;

       private String reason;
    public ErrorResponse(String errorMessage) {
        String[] parsedError = errorMessage.split(";");
        this.errorCode = parsedError[0];
        this.reason = parsedError[1];
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getReason() {
        return reason;
    }
}

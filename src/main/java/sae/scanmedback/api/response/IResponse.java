package sae.scanmedback.api.response;

public interface IResponse {
    String getStatus();

    Object getData();

    String getMessage();
}

package it.epicode.SpringSecurity.JWT.exception;

public class PrenotazioneDuplicataException extends RuntimeException {
    public PrenotazioneDuplicataException(String message) {
        super(message);
    }
}

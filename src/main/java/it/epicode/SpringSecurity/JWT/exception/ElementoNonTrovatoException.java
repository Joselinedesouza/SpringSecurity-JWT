package it.epicode.SpringSecurity.JWT.exception;

public class ElementoNonTrovatoException extends RuntimeException {
    public ElementoNonTrovatoException(String message) {
        super(message);
    }
}

package jkt.centralisateur.common.exception;

public class CentralisateurException extends Exception {
    private static final long serialVersionUID = 1L;

    public CentralisateurException() {
    }

    public CentralisateurException(final Exception cause) {
        super(cause);
    }
}

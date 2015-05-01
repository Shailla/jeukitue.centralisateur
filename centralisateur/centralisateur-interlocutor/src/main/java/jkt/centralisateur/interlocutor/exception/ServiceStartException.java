package jkt.centralisateur.interlocutor.exception;

import jkt.centralisateur.common.exception.CentralisateurException;

public class ServiceStartException extends CentralisateurException {
	private static final long serialVersionUID = 1L;
	
	public ServiceStartException(final Exception cause) {
	    super(cause);
	}
}

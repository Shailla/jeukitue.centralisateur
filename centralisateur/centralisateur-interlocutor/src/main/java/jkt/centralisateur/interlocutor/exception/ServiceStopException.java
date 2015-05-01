package jkt.centralisateur.interlocutor.exception;

import jkt.centralisateur.common.exception.CentralisateurException;

public class ServiceStopException extends CentralisateurException {
	private static final long serialVersionUID = 1L;
	
	public ServiceStopException(final Exception cause) {
	    super(cause);
	}
}

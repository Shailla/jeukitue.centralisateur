package jkt.centralisateur.interlocutor.exception;

import jkt.centralisateur.common.exception.CentralisateurException;

public class UnknownDataException extends CentralisateurException {
	private static final long serialVersionUID = 1L;
	public final int unknownCode;
	
	public UnknownDataException(final int unknownCode) {
		this.unknownCode = unknownCode;
	}
	
	public int getUnknownCode() {
		return unknownCode;
	}
}

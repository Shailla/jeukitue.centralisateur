package jkt.centralisateur.interlocutor.supervision.message;

import java.util.Date;

public class ErrorMessage implements SupervisionMessage {
	/** Date of the error */
	private final Date date;
	/** Description of the error */
	private final String message;
	
	/**
	 * Constructor
	 * @param message description of the error
	 */
	public ErrorMessage(final String message) {
		date = new Date();
		this.message = message;
	}

	/**
	 * Return the date of the error
	 * 
	 * @return date of the message
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Return the message of the error
	 * 
	 * @return description of the error
	 */
	public String getMessage() {
		return message;
	}

	@Override
	public boolean isError() {
		return true;
	}

	@Override
	public boolean isInfo() {
		return false;
	}

	@Override
	public boolean isWarning() {
		return false;
	}
}

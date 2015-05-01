package jkt.centralisateur.interlocutor.supervision.message;

import java.util.Date;

public class InfoMessage implements SupervisionMessage {
	/** Date of the error */
	private final Date date;
	/** Description of the information */
	private final String message;
	
	/**
	 * Constructor
	 * @param message description of the information
	 */
	public InfoMessage(final String message) {
		date = new Date();
		this.message = message;
	}

	/**
	 * Return the date of the information
	 * 
	 * @return date of the message
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Return the message of the information
	 * 
	 * @return description of the information
	 */
	public String getMessage() {
		return message;
	}

	@Override
	public boolean isError() {
		return false;
	}

	@Override
	public boolean isInfo() {
		return true;
	}

	@Override
	public boolean isWarning() {
		return false;
	}
}

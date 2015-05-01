package jkt.centralisateur.interlocutor.supervision.message;

import java.util.Date;

public class WarningMessage implements SupervisionMessage {
	/** Date of the warning */
	private final Date date;
	/** Description of the warning */
	private final String message;
	
	/**
	 * Constructor
	 * @param message description of the warning
	 */
	public WarningMessage(final String message) {
		date = new Date();
		this.message = message;
	}

	/**
	 * Return the date of the warning
	 * 
	 * @return date of the message
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Return the message of the warning
	 * 
	 * @return description of the warning
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
		return false;
	}

	@Override
	public boolean isWarning() {
		return true;
	}
}

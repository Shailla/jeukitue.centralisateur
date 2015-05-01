package jkt.centralisateur.interlocutor.supervision.message;

import java.util.Date;

public interface SupervisionMessage {
	Date getDate();
	String getMessage();
	
	boolean isWarning();
	boolean isInfo();
	boolean isError();
}

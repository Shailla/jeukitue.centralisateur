package jkt.centralisateur.interlocutor.supervision;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jkt.centralisateur.common.i18n.Messages;
import jkt.centralisateur.interlocutor.supervision.message.ErrorMessage;
import jkt.centralisateur.interlocutor.supervision.message.InfoMessage;
import jkt.centralisateur.interlocutor.supervision.message.SupervisionMessage;
import jkt.centralisateur.interlocutor.supervision.message.WarningMessage;

public class SupervisionManager {
	private final List<SupervisionMessage> logs = new ArrayList<SupervisionMessage>();
	private Messages messages;
	
	public void addError(final String key, final Object...arguments) {
		String message = messages.getFormattedMessage(key, arguments);
		SupervisionMessage supervisionMessage = new ErrorMessage(message);
		logs.add(supervisionMessage);
	}

	public void addWarning(final String key, final Object...arguments) {
		String message = messages.getFormattedMessage(key, arguments);
		SupervisionMessage supervisionMessage = new WarningMessage(message);
		logs.add(supervisionMessage);
	}
	
	public void addInfo(final String key, final Object...arguments) {
		String message = messages.getFormattedMessage(key, arguments);
		SupervisionMessage supervisionMessage = new InfoMessage(message);
		logs.add(supervisionMessage);
	}
	
	public Collection<SupervisionMessage> getLogs() {
		return logs;
	}
	
	public void clearLogs() {
		logs.clear();
	}

	public void setMessages(Messages messages) {
		this.messages = messages;
	}
}

package jkt.centralisateur.common.i18n;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class Messages {
	static final Logger log = Logger.getLogger(Messages.class);
	
	private ResourceBundle resourceBundle;
	
	public String getFormattedMessage(final String key,
							   final Object...arguments) {
		String pattern = resourceBundle.getString(key);
		String formattedMessage = MessageFormat.format(pattern, arguments);
		return formattedMessage;
	}
	
	public void setBundleName(final String resourceBundleName) {
		resourceBundle = ResourceBundle.getBundle(resourceBundleName);
		
		if(resourceBundle == null) {
			log.error("ResourceBundle introuvable [resourceBundleName='" + resourceBundleName + "']");
		}
	}
}

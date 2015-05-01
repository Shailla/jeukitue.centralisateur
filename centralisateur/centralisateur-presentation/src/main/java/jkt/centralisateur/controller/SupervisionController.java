package jkt.centralisateur.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jkt.centralisateur.interlocutor.ServiceManager;
import jkt.centralisateur.interlocutor.supervision.message.SupervisionMessage;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class SupervisionController implements Controller {
	private ServiceManager serviceManager;

	public ModelAndView handleRequest(HttpServletRequest request,
								      HttpServletResponse response) throws Exception {
		// Get commands
		String clear = request.getParameter("clear");
		if(clear != null) {
		    serviceManager.clearSupervisionMessages();	
		}		
		
		// Show supervision messages
		Collection<SupervisionMessage> messages = serviceManager.getSupervisionMessages();
		
		List<SupervisionMessage> messagesList = new ArrayList<SupervisionMessage>();
		messagesList.addAll(messages);
		Collections.reverse(messagesList);
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("messages", messagesList);
		
		ModelAndView modelAndView = new ModelAndView("supervision", model);
		
		return modelAndView;
	}

	public void setServiceManager(ServiceManager serviceManager) {
		this.serviceManager = serviceManager;
	}

}

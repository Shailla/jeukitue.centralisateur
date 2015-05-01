package jkt.centralisateur.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jkt.centralisateur.interlocutor.ServiceManager;
import jkt.centralisateur.interlocutor.scoreboard.ScoreboardStateDto;
import jkt.centralisateur.interlocutor.scoreboard.State;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ServicesScoreboardController implements Controller {
	private ServiceManager serviceManager;

	public ModelAndView handleRequest(HttpServletRequest request,
								      HttpServletResponse response) throws Exception {	
		// Get commands
        String startAllServices = request.getParameter("startAllServices");
        String stopAllServices = request.getParameter("stopAllServices");
	    String startOneService = request.getParameter("startOneService");
		String stopOneService = request.getParameter("stopOneService");
		
        if(startAllServices != null) {
            serviceManager.startAllServices();
        }
        else if(stopAllServices != null) {
            serviceManager.stopAllServices();
        }
        else if(startOneService != null) {
		    String serviceName = request.getParameter("serviceName");
			serviceManager.startServiceByName(serviceName);
		}
		else if(stopOneService != null) {
		    String serviceName = request.getParameter("serviceName");
			serviceManager.stopServiceByName(serviceName);
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		// Etat global de l'interlocuteur 
		ScoreboardStateDto scoreboardState = serviceManager.getState();
		State scoreboardMainState = scoreboardState.getState();
		model.put("scoreboardMainState", scoreboardMainState);
		
		// Etat de chaque listener de l'interlocuteur
		Map<String, State> servicesStates = scoreboardState.getListenerStates();		
		model.put("servicesStates", servicesStates);
		
		ModelAndView modelAndView = new ModelAndView("servicesScoreboard", model);
		
		return modelAndView;
	}

	public void setServiceManager(ServiceManager serviceManager) {
		this.serviceManager = serviceManager;
	}
}

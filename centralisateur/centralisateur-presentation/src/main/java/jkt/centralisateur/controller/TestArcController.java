package jkt.centralisateur.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jkt.centralisateur.storage.buisiness.TestArcService;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class TestArcController implements Controller {
	static final Logger log = Logger.getLogger(TestArcController.class);
	
	private TestArcService testArcService;

	public ModelAndView handleRequest(HttpServletRequest request,
								      HttpServletResponse response) throws Exception {
		log.error("Je suis là 1");
		testArcService.enregistrerUnTruc();
		log.error("Je suis là 2");
		
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		ModelAndView modelAndView = new ModelAndView("testArc", model);
		
		log.error("Je suis là 3");
		
		return modelAndView;
	}

	public void setTestArcService(TestArcService testArcService) {
		this.testArcService = testArcService;
	}

}

package jkt.centralisateur.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jkt.centralisateur.storage.common.PerformanceCollector;
import jkt.centralisateur.storage.common.PerformanceData;
import jkt.centralisateur.storage.common.PerformanceDataKey;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class PerformanceMonitorController implements Controller {
	static final Logger log = Logger.getLogger(PerformanceMonitorController.class);
	
	private PerformanceCollector performanceCollector;

	public ModelAndView handleRequest(HttpServletRequest request,
								      HttpServletResponse response) throws Exception {
		Map<PerformanceDataKey, PerformanceData> data = performanceCollector.getData();
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		model.put("data", data);
		
		ModelAndView modelAndView = new ModelAndView("performanceMonitor", model);
		
		return modelAndView;
	}

	public void setPerformanceCollector(PerformanceCollector performanceCollector) {
		this.performanceCollector = performanceCollector;
	}
}

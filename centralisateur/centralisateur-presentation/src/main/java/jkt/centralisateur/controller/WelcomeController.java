package jkt.centralisateur.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jkt.centralisateur.authentication.UserData;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WelcomeController {

    @RequestMapping("/secure/welcome.do")
	public ModelAndView handleRequest(HttpServletRequest request,
								      HttpServletResponse response) throws Exception {
		final SecurityContext context = SecurityContextHolder.getContext();
		final Authentication authentification = context.getAuthentication();
		final UserData userData = (UserData) authentification.getPrincipal();
		final boolean isAdmin = userData.isAdmin();
		
		final ModelAndView mav;
		
		if(isAdmin){
		    mav = new ModelAndView("welcomeAdmin");
		}
		else {
		    mav = new ModelAndView("welcome");
		}
		
		return mav;
	}
}

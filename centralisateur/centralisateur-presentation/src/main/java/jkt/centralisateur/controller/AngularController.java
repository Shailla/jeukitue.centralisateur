package jkt.centralisateur.controller;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class AngularController {

	@XmlRootElement
	public static class EssaiDto {
		private String var;

		@XmlAttribute
		public String getVar() {
			return var;
		}

		public void setVar(String var) {
			this.var = var;
		}
	}

	@RequestMapping("/angular/angular.do")
	public String welcome() {
		return "welcomeAngular";
	}
	
	@RequestMapping(value="/angular/essai.do", method=RequestMethod.GET, produces={"application/xml", "application/json"})
	@ResponseStatus(value=HttpStatus.OK)
	public @ResponseBody EssaiDto essai() {
		EssaiDto var = new EssaiDto();
		var.setVar("Coucou");
		return var;
	}
}

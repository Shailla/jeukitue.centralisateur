package jkt.centralisateur.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jkt.centralisateur.storage.service.UserService;
import jkt.centralisateur.storage.service.UserService.ConfirmeUserResult;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ValidationInscriptionController implements Controller {
    static public final String CONFIRMATION_CODE_PARAM = "confirmationCode";
    static public final String USER_LOGIN_PARAM = "userLogin";
    
    private UserService userService;

    @Override
    public ModelAndView handleRequest(final HttpServletRequest request,
                                      final HttpServletResponse response) throws Exception {
        final ModelAndView mav;
        final ConfirmeUserResult result;
        String confirmationCode = request.getParameter(CONFIRMATION_CODE_PARAM);
        String login = request.getParameter(USER_LOGIN_PARAM);
        
        if(confirmationCode != null && login != null) {
            result = userService.confirmeUser(login, confirmationCode);
        }
        else {
            result = ConfirmeUserResult.ECHEC;
        }
        
        if(ConfirmeUserResult.SUCCES.equals(result)) {
            mav = new ModelAndView("inscriptionConfirmationOk");
        }
        else if(ConfirmeUserResult.DEJA_INSCRIT.equals(result)) {
            mav = new ModelAndView("inscriptionConfirmationDejaInscrit");
        }
        else {
            mav = new ModelAndView("inscriptionConfirmationFailed"); 
        }
        
        return mav;
    }
    
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}

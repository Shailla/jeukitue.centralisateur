package jkt.centralisateur.controller;

import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import jkt.centralisateur.common.Constantes;
import jkt.centralisateur.mail.InscriptionConfirmationEmail;
import jkt.centralisateur.storage.dto.UserDto;
import jkt.centralisateur.storage.result.CreateUserResult;
import jkt.centralisateur.storage.service.UserService;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class InscriptionController extends SimpleFormController {
    private UserService userService;
    private MailSender mailSender;
    private InscriptionConfirmationEmail inscriptionConfirmationEmail;

    @Override
    protected void onBindAndValidate(final HttpServletRequest request,
                                     final Object command, 
                                     final BindException errors) throws Exception {
        UserDto user = (UserDto) command;

        // Validation du formulaire
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "erreur.global.champObligatoire");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "erreur.global.champObligatoire");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "erreur.global.champObligatoire");
        
        if(errors.getErrorCount() == 0) {
            // Suppression des espaces de début et fin
            user.setLogin(user.getLogin().trim());
            user.setPassword(user.getPassword().trim());
            String emailUser = user.getEmail().trim();
            user.setEmail(emailUser);
            
            Set<String> roles = new HashSet<String>();
            roles.add(Constantes.ROLE_USER);
            user.setRoles(roles);
            
            // Génère un identifiant unique pour permettre à l'utilisateur de valider son inscription par mail
            StringBuilder codeValidationBuilder = new StringBuilder();
            UUID uuid = UUID.randomUUID();
            codeValidationBuilder.append(Math.abs(uuid.getLeastSignificantBits()));
            codeValidationBuilder.append(Math.abs(uuid.getMostSignificantBits()));
            final String codeValidation = codeValidationBuilder.toString();
            user.setUuidInscription(codeValidation);
            
            // Construction et envoi du mail de confirmation
            StringBuilder lien = new StringBuilder();
            lien.append("http://localhost:8080/centralisateur-war/inscription_confirmation.do");
            lien.append("?");
            lien.append(URLEncoder.encode("userLogin", "UTF-8")).append("=").append(URLEncoder.encode(user.getLogin(), "UTF-8"));
            lien.append("&");
            lien.append(URLEncoder.encode("confirmationCode", "UTF-8")).append("=").append(URLEncoder.encode(codeValidation, "UTF-8"));
            
            try {
                SimpleMailMessage mail = inscriptionConfirmationEmail.construct(lien, emailUser);
                mailSender.send(mail);                
            }
            catch(MailException exception) {
                errors.reject("erreur.global");
            }

            
            // Création du nouvel utilisateur
            if(errors.getErrorCount() == 0) {
                CreateUserResult result = userService.createUser(user);
                
                // Validation de l'action de création       
                if(CreateUserResult.SUCCESS.equals(result)) {
                    // Ok, nothing to do
                }
                else if(CreateUserResult.ALREADY_EXISTING_USER.equals(result)) {
                    errors.reject("erreur.manageUser.addUser");
                }
                else {
                    errors.reject("erreur.global");
                }
            }
        }
        
        super.onBindAndValidate(request, command, errors);
    }
    
    public void setUserService(final UserService userService) {
        this.userService = userService;
    }
    
    public void setMailSender(final MailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    public void setInscriptionConfirmationEmail(
            InscriptionConfirmationEmail inscriptionConfirmationEmail) {
        this.inscriptionConfirmationEmail = inscriptionConfirmationEmail;
    }
}

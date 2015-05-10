package jkt.centralisateur.controller;

import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jkt.centralisateur.common.Constantes;
import jkt.centralisateur.mail.InscriptionConfirmationEmail;
import jkt.centralisateur.storage.dto.UserDto;
import jkt.centralisateur.storage.result.CreateUserResult;
import jkt.centralisateur.storage.service.UserService;

import org.apache.commons.lang.StringUtils;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InscriptionController {
    private UserService userService;
    private MailSender mailSender;
    private InscriptionConfirmationEmail inscriptionConfirmationEmail;

    @RequestMapping(value="/inscription.do")
    public String inscription(final UserDto user, 
                              final Errors errors) throws Exception {
        // Validation du formulaire
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "erreur.global.champObligatoire");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "erreur.global.champObligatoire");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "erreur.global.champObligatoire");
        
        if(errors.getErrorCount() == 0) {
            // Suppression des espaces de début et fin
            user.setLogin(StringUtils.trim(user.getLogin()));
            user.setPassword(StringUtils.trim(user.getPassword()));
            String emailUser = StringUtils.trim(user.getEmail());
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
        
        return "inscriptionConfirmation";
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

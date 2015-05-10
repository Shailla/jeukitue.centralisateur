package jkt.centralisateur.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jkt.centralisateur.common.Constantes;
import jkt.centralisateur.storage.dto.UserDto;
import jkt.centralisateur.storage.result.CreateUserResult;
import jkt.centralisateur.storage.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/secure/admin/")
public class UserManagerController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    /**
     * Affiche la liste des utilisateurs.
     */
    @RequestMapping("manage_users.do")
    public ModelAndView listUsers() {
        Set<UserDto> users = userService.loadUsers(0, 100);	// TODO paginer la page d'affichage des utilisateurs

        Map<String, Object> model = new HashMap<String, Object>();		
        model.put("users", users);

        ModelAndView modelAndView = new ModelAndView("manageUsers", model);

        return modelAndView;
    }

    /**
     * Affiche la page de création d'un nouvel utilisateur.
     */
    @RequestMapping("add_user.do")
    public ModelAndView addUser(final UserDto userDto,
                                final Errors errors) {
        return new ModelAndView("addUser", "userDto", userDto);
    }

    /**
     * Enregistre un nouvel utilisateur.
     */
    @RequestMapping("save_user.do")
    public ModelAndView saveUser(final UserDto userDto,
                                 final Errors errors) {
        // Validation du formulaire
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", Constantes.MSG_CHAMP_OBLIGATOIRE);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", Constantes.MSG_CHAMP_OBLIGATOIRE);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", Constantes.MSG_CHAMP_OBLIGATOIRE);
        ValidationUtils.rejectIfEmpty(errors, "roles", Constantes.MSG_CHAMP_OBLIGATOIRE);

        // Création du nouvel utilisateur
        if(errors.getErrorCount() == 0) {
            // Suppression des espaces de début et fin
            userDto.setLogin(userDto.getLogin().trim());
            userDto.setPassword(userDto.getPassword().trim());
            userDto.setEmail(userDto.getEmail().trim());

            final CreateUserResult result = userService.createUser(userDto);

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

        if(errors.hasErrors()) {
            return addUser(userDto, errors);
        }
        else {
            return new ModelAndView("redirect:/secure/admin/manage_users.do");
        }
    }
    
    /**
     * Enregistre les modifications d'un utilisateur.
     */
    @RequestMapping("save_modify_user.do")
    public ModelAndView saveModifyUser(final UserDto userDto,
                                       final Errors errors) {
        // Validation du formulaire
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", Constantes.MSG_CHAMP_OBLIGATOIRE);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", Constantes.MSG_CHAMP_OBLIGATOIRE);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", Constantes.MSG_CHAMP_OBLIGATOIRE);
        ValidationUtils.rejectIfEmpty(errors, "roles", Constantes.MSG_CHAMP_OBLIGATOIRE);

        // Création du nouvel utilisateur
        if(errors.getErrorCount() == 0) {
            // Suppression des espaces de début et fin
            userDto.setLogin(userDto.getLogin().trim());
            userDto.setPassword(userDto.getPassword().trim());
            userDto.setEmail(userDto.getEmail().trim());

            userService.modifyUser(userDto);
        }

        if(errors.hasErrors()) {
            return new ModelAndView("modifyUser", "userDto", userDto);
        }
        else {
            return new ModelAndView("redirect:redirect:/secure/admin/manage_users.do");
        }
    }
    
    /**
     * Affiche la page de modification d'un utilisateur.
     */
    @RequestMapping("modify_user.do")
    public ModelAndView modifyUser(final @RequestParam("user_id") long userId) {
        final UserDto userDto = userService.loadUser(userId);
        
        return new ModelAndView("modifyUser", "userDto", userDto);
    }
    
    /**
     * Supprime un utilisateur.
     */
    @RequestMapping("remove_user.do")
    public ModelAndView removeUser(final @RequestParam("user_id") long userId,
                                   final @RequestParam("user_login") String userLogin) {        
        userService.removeUser(userId);
        
        return new ModelAndView("removeUserConfirmation", "username", userLogin);
    }
}

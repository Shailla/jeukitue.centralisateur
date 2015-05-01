package jkt.centralisateur.storage.service;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.transaction.annotation.Transactional;

import jkt.centralisateur.storage.buisiness.UserBuisiness;
import jkt.centralisateur.storage.dto.UserDto;
import jkt.centralisateur.storage.model.Profile;
import jkt.centralisateur.storage.model.User;
import jkt.centralisateur.storage.result.CreateUserResult;

public class UserService {
    private UserBuisiness userBuisiness;

    static public enum ConfirmeUserResult {
        /** Réussite de validation de l'inscription */
        SUCCES,

        /** Utilisateur déjà inscrit et confirmé */
        DEJA_INSCRIT,

        /** Echec de confirmation de l'inscription */
        ECHEC
    }

    @Transactional(readOnly=true)
    public UserDto loadUser(final String username) {
        User user = userBuisiness.getUser(username);

        UserDto userDto;
        if(user != null) {
            userDto = new UserDto();
            userDto.setUserId(user.getId());
            userDto.setLogin(user.getLogin());
            userDto.setPassword(user.getPassword());
            userDto.setEmail(user.getEmail());
            userDto.setEnabled(user.isEnabled());
            userDto.setUuidInscription(user.getUuidInscription());

            Set<String> roles = new TreeSet<String>();
            Set<Profile> profiles = user.getProfiles();

            if(profiles != null) {
                for(Profile profile : profiles) {
                    String role = profile.getNom();
                    roles.add(role);
                }			
            }

            userDto.setRoles(roles);
        } else {
            userDto = null;
        }

        return userDto;
    }

    @Transactional(readOnly=true)
    public UserDto loadUser(final Long userId) {
        User user = userBuisiness.getUser(userId);

        UserDto userDto;
        if(user != null) {
            userDto = new UserDto();
            userDto.setUserId(user.getId());
            userDto.setLogin(user.getLogin());
            userDto.setPassword(user.getPassword());
            userDto.setEmail(user.getEmail());
            userDto.setEnabled(user.isEnabled());
            userDto.setUuidInscription(user.getUuidInscription());

            Set<String> roles = new TreeSet<String>();
            Set<Profile> profiles = user.getProfiles();

            if(profiles != null) {
                for(Profile profile : profiles) {
                    String role = profile.getNom();
                    roles.add(role);
                }			
            }

            userDto.setRoles(roles);
        } else {
            userDto = null;
        }

        return userDto;
    }

    @Transactional(readOnly=true)
    public Set<UserDto> loadUsers(final int firstUser, final int nbrUsers) {
        List<User> users = userBuisiness.getUsers(firstUser, nbrUsers);

        Set<UserDto> userList = new TreeSet<UserDto>();
        for(User user : users) {
            UserDto userDto = new UserDto();	
            userDto.setUserId(user.getId());
            userDto.setLogin(user.getLogin());
            userDto.setPassword(user.getPassword());
            userDto.setEmail(user.getEmail());
            userDto.setEnabled(user.isEnabled());
            userDto.setUuidInscription(user.getUuidInscription());

            Set<String> roles = new TreeSet<String>();
            userDto.setRoles(roles);

            if(user.getProfiles() != null) {
                for(Profile profile : user.getProfiles()) {
                    roles.add(profile.getNom());
                }
            }

            userList.add(userDto);
        }

        return userList;
    }

    @Transactional(readOnly=false)
    public CreateUserResult createUser(final UserDto userDto) {
        String username = userDto.getLogin();
        String password = userDto.getPassword();
        String email = userDto.getEmail();
        String uuidInscription = userDto.getUuidInscription();

        Set<String> roles = userDto.getRoles();

        return userBuisiness.createUser(username, password, email, uuidInscription, false, roles);	
    }
    
    @Transactional(readOnly=false)
    public CreateUserResult jktCreateUser(final UserDto userDto) {
        String username = userDto.getLogin();
        String password = userDto.getPassword();
        String email = userDto.getEmail();
        String uuidInscription = userDto.getUuidInscription();

        Set<String> roles = userDto.getRoles();

        return userBuisiness.createUser(username, password, email, uuidInscription, false, roles);  
    }

    @Transactional(readOnly=false)
    public void modifyUser(final UserDto userDto) {
        final Long userId = userDto.getUserId();
        String username = userDto.getLogin();
        String password = userDto.getPassword();
        String email = userDto.getEmail();

        Set<String> roles = userDto.getRoles();

        userBuisiness.modifyUser(userId, username, password, email, roles);
    }

    @Transactional(readOnly=false)
    public ConfirmeUserResult confirmeUser(String login, String confirmationCode) {
        ConfirmeUserResult result = ConfirmeUserResult.ECHEC;
        final User user = userBuisiness.getUser(login);

        if(user.isEnabled()) {
            result = ConfirmeUserResult.DEJA_INSCRIT;
        }
        else {
            String uuidInscription = user.getUuidInscription();
            if(user.getUuidInscription() != null) {
                if(uuidInscription.equals(confirmationCode)) {
                    result = ConfirmeUserResult.SUCCES;
                }
            }	        
        }

        if(ConfirmeUserResult.SUCCES.equals(result)) {
            user.setEnabled(true);
            userBuisiness.modifyUser(user);
        }

        return result;
    }

    @Transactional(readOnly=false)
    public void removeUser(final Long userId) {
        userBuisiness.removeUser(userId);
    }

    public void setUserBuisiness(UserBuisiness userBuisiness) {
        this.userBuisiness = userBuisiness;
    }
}

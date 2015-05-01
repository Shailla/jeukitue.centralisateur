package jkt.centralisateur.authentication;

import jkt.centralisateur.storage.dto.UserDto;
import jkt.centralisateur.storage.service.UserService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;

public class UserAuthenticater implements UserDetailsService {
	private UserService userService;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {

		UserDto user = userService.loadUser(username);
		if(user == null) {
			throw new UsernameNotFoundException("Unknown user '" + username + "'");
		}
		
		UserDetails userDetails = new UserData(user);
		return userDetails;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}

package jkt.centralisateur.authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import jkt.centralisateur.storage.common.Constants;
import jkt.centralisateur.storage.dto.UserDto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;;

public class UserData implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private boolean enabled;
	private List<GrantedAuthority> grantedAuthorities;
	
	UserData(final UserDto user) {
		username = user.getLogin();
		password = user.getPassword();
		enabled = user.isEnabled();
		
		Set<String> roles= user.getRoles();
		grantedAuthorities = new ArrayList<GrantedAuthority>();
		
		for(String role : roles) {
			grantedAuthorities.add(new GrantedAuthorityImpl(role));
		}
	}
	
	public Collection<GrantedAuthority> getAuthorities() {
		return grantedAuthorities;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return enabled;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

	public boolean isAdmin() {
		boolean isAdmin = false;
		
		for(GrantedAuthority authority : grantedAuthorities) {
			String role = authority.getAuthority();
			
			if(Constants.ROLE_ADMIN.equals(role)) {
				isAdmin = true;
				break;
			}
		}
		
		return isAdmin;
	}
}

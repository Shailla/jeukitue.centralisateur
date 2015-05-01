package jkt.centralisateur.storage.dto;

import java.util.Set;

public class UserDto implements Comparable<UserDto> {
	private Long userId;
	private String login;
	private String password;
	private String email;
	private String uuidInscription;
	private boolean enabled;
    private Set<String> roles;
    
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Set<String> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	
	public int compareTo(UserDto user) {
		int result;

		if(user != null && login != null && user.login != null) {
			result = login.compareTo(user.login);
		} else if(login != null) {
			result = 1;
		} else {
			result = -1;
		}
		
		return result;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getUuidInscription() {
	    return uuidInscription;
	}

	public void setUuidInscription(String uuidInscription) {
	    this.uuidInscription = uuidInscription;
	}
}

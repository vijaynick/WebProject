package om.gov.moh.eab.security.web.bean;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import om.gov.moh.eab.abstracts.bean.AbstractMB;
import om.gov.moh.eab.constants.EABConstants;
import om.gov.moh.eab.security.bo.UserManager;
import om.gov.moh.eab.security.vo.UserVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session")
public class LoginManagedBean extends AbstractMB implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserManager userManager;
	private boolean authorized;
	private UserVO currentUser=new UserVO();
	public void login() {
		try {
			Authentication request = new UsernamePasswordAuthenticationToken(currentUser.getLoginId(),currentUser.getPassword());
			Authentication result = authenticationManager.authenticate(request);
			SecurityContextHolder.getContext().setAuthentication(result);
			redirectUserAsPerRole();
			authorized=true;
		} catch (BadCredentialsException be) {
			be.printStackTrace();
			displayErrorMessageToUser("login.usernamepassworddontmatch");
			authorized=false;
		} catch (AuthenticationException e) {
			e.printStackTrace();
			displayErrorMessageToUser("common.error");
			authorized=false;
		}

	}

	private void redirectUserAsPerRole() {
		try {
			currentUser = userManager.findUserByLoginId(currentUser.getLoginId());
			if (EABConstants.ROLE_SUPER_ADMIN.equals(currentUser.getRoleVO().getRoleId()) || EABConstants.ROLE_ADMIN.equals(currentUser.getRoleVO().getRoleId())) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("pages/person/home.xhtml");
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public boolean isAuthorized() {
		return authorized;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}


	public UserVO getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(UserVO currentUser) {
		this.currentUser = currentUser;
	}

	
}

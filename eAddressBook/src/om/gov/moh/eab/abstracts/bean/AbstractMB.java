package om.gov.moh.eab.abstracts.bean;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import om.gov.moh.eab.utils.JSFMessageUtil;

import org.primefaces.context.RequestContext;

public class AbstractMB {

	public AbstractMB() {
		super();
	}

	protected void displayErrorMessageToUser(String key) {
		JSFMessageUtil messageUtil = new JSFMessageUtil();
		messageUtil.sendErrorMessageToUser(key);
	}

	protected void displayInfoMessageToUser(String key) {
		JSFMessageUtil messageUtil = new JSFMessageUtil();
		messageUtil.sendInfoMessageToUser(key);
	}

	protected RequestContext getRequestContext() {
		return RequestContext.getCurrentInstance();
	}
	
	public static String getUserIP(){
		HttpServletRequest request=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		 String ipAddress = request.getHeader("X-FORWARDED-FOR");  
		   if (ipAddress == null) {  
			   ipAddress = request.getRemoteAddr();  
		   }
		   
		return ipAddress;
	}
}
package om.gov.moh.eab.utils;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class JSFMessageUtil {

	public void sendInfoMessageToUser(String key) {
		FacesMessage facesMessage = createMessage(FacesMessage.SEVERITY_INFO, getMessageFromKey(key), "");
		addMessageToJsfContext(facesMessage);
	}

	public void sendErrorMessageToUser(String key) {
		FacesMessage facesMessage = createMessage(FacesMessage.SEVERITY_ERROR, getMessageFromKey(key), "");
		addMessageToJsfContext(facesMessage);
	}

	private FacesMessage createMessage(Severity severity, String summary, String detail) {
		return new FacesMessage(severity, summary, detail);

	}

	private void addMessageToJsfContext(FacesMessage facesMessage) {
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}

	public String getMessageFromKey(String key) {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
		String message = bundle.getString(key);
		return message;

	}
}
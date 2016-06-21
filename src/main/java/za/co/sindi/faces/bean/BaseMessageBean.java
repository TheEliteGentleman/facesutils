/**
 * 
 */
package za.co.sindi.faces.bean;

import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * @author Bienfait Sindi
 * @since 08 January 2013
 *
 */
public abstract class BaseMessageBean {

	protected final Logger LOGGER = Logger.getLogger(this.getClass().getName());
	
	/**
	 * Return the current {@link FacesContext}.
	 * 
	 * @return the current {@link FacesContext}.
	 */
	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	/**
	 * Adds the specified message into the appropriate context
	 * for use by the &lt;h:messages&gt; tag (if
	 * globalOnly="true" is set and clientId=null), or to the specified clientId.
	 * 
	 * @param clientId
	 * @param message
	 */
	protected void addMessage(String clientId, FacesMessage message) {
		if (message != null) {
//			getFacesContext().addMessage(clientId == null ? null : component.getClientId(getFacesContext()), message);
			getFacesContext().addMessage(clientId, message);
		}
	}
	
	protected void addInfoMessage(String summary) {
		FacesMessage message = new FacesMessage(summary);
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		addMessage(null, message);
	}
	
	protected void addInfoMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(summary, detail);
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		addMessage(null, message);
	}
	
//	protected void addInfoMessage(String clientId, String summary) {
//		FacesMessage message = new FacesMessage(summary);
//		message.setSeverity(FacesMessage.SEVERITY_INFO);
//		addMessage(clientId, message);
//	}
	
	protected void addInfoMessage(String clientId, String summary, String detail) {
		FacesMessage message = new FacesMessage(summary, detail);
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		addMessage(clientId, message);
	}
	
	protected void addWarnMessage(String summary) {
		FacesMessage message = new FacesMessage(summary);
		message.setSeverity(FacesMessage.SEVERITY_WARN);
		addMessage(null, message);
	}
	
	protected void addWarnMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(summary, detail);
		message.setSeverity(FacesMessage.SEVERITY_WARN);
		addMessage(null, message);
	}
	
//	protected void addWarnMessage(String clientId, String summary) {
//		FacesMessage message = new FacesMessage(summary);
//		message.setSeverity(FacesMessage.SEVERITY_WARN);
//		addMessage(clientId, message);
//	}
	
	protected void addWarnMessage(String clientId, String summary, String detail) {
		FacesMessage message = new FacesMessage(summary, detail);
		message.setSeverity(FacesMessage.SEVERITY_WARN);
		addMessage(clientId, message);
	}
	
	protected void addErrorMessage(String summary) {
		FacesMessage message = new FacesMessage(summary);
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		addMessage(null, message);
	}
	
	protected void addErrorMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(summary, detail);
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		addMessage(null, message);
	}
	
//	protected void addErrorMessage(String clientId, String summary) {
//		FacesMessage message = new FacesMessage(summary);
//		message.setSeverity(FacesMessage.SEVERITY_ERROR);
//		addMessage(clientId, message);
//	}
	
	protected void addErrorMessage(String clientId, String summary, String detail) {
		FacesMessage message = new FacesMessage(summary, detail);
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		addMessage(clientId, message);
	}
	
	protected void addFatalMessage(String summary) {
		FacesMessage message = new FacesMessage(summary);
		message.setSeverity(FacesMessage.SEVERITY_FATAL);
		addMessage(null, message);
	}
	
	protected void addFatalMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(summary, detail);
		message.setSeverity(FacesMessage.SEVERITY_FATAL);
		addMessage(null, message);
	}
	
//	protected void addFatalMessage(String clientId, String summary) {
//		FacesMessage message = new FacesMessage(summary);
//		message.setSeverity(FacesMessage.SEVERITY_FATAL);
//		addMessage(clientId, message);
//	}
	
	protected void addFatalMessage(String clientId, String summary, String detail) {
		FacesMessage message = new FacesMessage(summary, detail);
		message.setSeverity(FacesMessage.SEVERITY_FATAL);
		addMessage(clientId, message);
	}
}

/**
 * 
 */
package za.co.sindi.faces.bean;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

import za.co.sindi.common.utils.Locales;
import za.co.sindi.common.utils.PreConditions;
import za.co.sindi.faces.utils.FacesUtils;

/**
 * @author Bienfait Sindi
 * @since 05 February 2015
 *
 */
@Named("localeManager")
@SessionScoped
public class LocaleManager extends BaseMessageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8494362782258877045L;
	
	private Locale locale = FacesUtils.getLocale(getFacesContext());
	
	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
		FacesUtils.setLocale(getFacesContext(), locale);
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return locale.getLanguage();
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
//		setLocale(new Locale(language));
		setLocale(Locales.newLocale(language));
	}
	
	public void localeChanged(ValueChangeEvent event) {
		PreConditions.checkState(event != null, "No ValueChangeEvent received.");
		setLocale((Locale) event.getNewValue());
	}
	
	public void languageChanged(ValueChangeEvent event) {
		PreConditions.checkState(event != null, "No ValueChangeEvent received.");
		setLanguage(event.getNewValue().toString());
	}
}

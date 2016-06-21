/**
 * 
 */
package za.co.sindi.faces.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIInput;
import javax.faces.component.behavior.ClientBehaviorHolder;

import za.co.sindi.faces.validator.ReCaptchaValidator;

/**
 * @author Bienfait Sindi
 * @since 29 March 2014
 *
 */
@FacesComponent(InputReCaptcha.COMPONENT_TYPE)
public class InputReCaptcha extends UIInput implements ClientBehaviorHolder {

	public static final String COMPONENT_TYPE = "za.co.sindi.faces.InputReCaptcha";
	public static final String RENDERER_TYPE = "za.co.sindi.faces.InputReCaptcha";
	
	private enum PropertyKeys {
		publicKey,
		privateKey,
		theme,
		lang,
		tabindex,
		secure
	}

	/**
	 * 
	 */
	public InputReCaptcha() {
		super();
		// TODO Auto-generated constructor stub
		setRendererType(RENDERER_TYPE);
		addValidator(new ReCaptchaValidator());
	}

	/**
	 * @return the publicKey
	 */
	public String getPublicKey() {
		return (String) getStateHelper().eval(PropertyKeys.publicKey);
	}
	
	/**
	 * @param publicKey the publicKey to set
	 */
	public void setPublicKey(String publicKey) {
		getStateHelper().put(PropertyKeys.publicKey, publicKey);
	}
	
	/**
	 * @return the privateKey
	 */
	public String getPrivateKey() {
		return (String) getStateHelper().eval(PropertyKeys.privateKey);
	}
	
	/**
	 * @param privateKey the privateKey to set
	 */
	public void setPrivateKey(String privateKey) {
		getStateHelper().put(PropertyKeys.privateKey, privateKey);
	}

	/**
	 * @return the theme
	 */
	public String getTheme() {
		return (String) getStateHelper().eval(PropertyKeys.theme, "red");
	}

	/**
	 * @param theme the theme to set
	 */
	public void setTheme(String theme) {
		getStateHelper().put(PropertyKeys.theme, theme);
	}

	/**
	 * @return the lang
	 */
	public String getLang() {
		return (String) getStateHelper().eval(PropertyKeys.lang, "en");
	}

	/**
	 * @param lang the lang to set
	 */
	public void setLang(String lang) {
		getStateHelper().put(PropertyKeys.lang, lang);
	}

	/**
	 * @return the tabindex
	 */
	public int getTabindex() {
		return (Integer) getStateHelper().eval(PropertyKeys.tabindex, 0);
	}

	/**
	 * @param tabindex the tabindex to set
	 */
	public void setTabindex(int tabindex) {
		getStateHelper().put(PropertyKeys.tabindex, tabindex);
	}

	/**
	 * @return the secure
	 */
	public boolean isSecure() {
		return (Boolean) getStateHelper().eval(PropertyKeys.secure, false);
	}

	/**
	 * @param secure the secure to set
	 */
	public void setSecure(boolean secure) {
		getStateHelper().put(PropertyKeys.secure, secure);
	}
}

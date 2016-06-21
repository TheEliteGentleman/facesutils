/**
 * 
 */
package za.co.sindi.faces.validator;

import java.util.ResourceBundle;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import za.co.sindi.common.utils.Strings;
import za.co.sindi.faces.component.InputReCaptcha;
import za.co.sindi.faces.utils.FacesUtils;
import za.co.sindi.faces.value.SubmittedReCaptchaValue;
import za.co.sindi.recaptcha.Constants;
import za.co.sindi.recaptcha.ReCaptchaAnswerValidator;
import za.co.sindi.recaptcha.ReCaptchaResponse;
import za.co.sindi.recaptcha.impl.DefaultReCaptchaAnswerValidator;

/**
 * @author Bienfait Sindi
 * @since 30 March 2014
 *
 */
@FacesValidator(ReCaptchaValidator.VALIDATOR_ID)
public class ReCaptchaValidator implements Validator {
	
	public static final String VALIDATOR_ID = "za.co.sindi.faces.validator.ReCaptchaValidator";
	private static final String RECAPTCHA_BUNDLE_NAME = "za.co.sindi.properties.reCAPTCHA";
	
	/* (non-Javadoc)
	 * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		// TODO Auto-generated method stub
		if (context == null) {
			throw new IllegalArgumentException("FacesContext is null.");
		}
		
		if (component == null) {
			throw new IllegalArgumentException("UIComponent is null.");
		}
		
		ResourceBundle resourceBundle = ResourceBundle.getBundle(RECAPTCHA_BUNDLE_NAME); //context.getApplication().getResourceBundle(context, "reCAPTCHA");
		if (resourceBundle == null) {
			throw new NullPointerException("No ResourceBundle found " + RECAPTCHA_BUNDLE_NAME);
		}

		SubmittedReCaptchaValue submittedValue = (SubmittedReCaptchaValue) value;
		InputReCaptcha reCaptcha = (InputReCaptcha) component;
		String privateKey = reCaptcha.getPrivateKey();
		if (Strings.isNullOrEmpty(privateKey)) {
			throw new FacesException("reCAPTCHA API requires a private key.");
		}
		
		if (Strings.isNullOrEmpty(submittedValue.getChallenge())) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("validation-failed"), resourceBundle.getString("recaptcha_challenge_field_invalid")));
		}
		
//		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		String verifyApiUrl = (reCaptcha.isSecure() || context.getExternalContext().isSecure() ? "https" : "http") + "://" + Constants.RECAPTCHA_API_SERVER_URL + "/verify";
		ReCaptchaAnswerValidator validator = new DefaultReCaptchaAnswerValidator();
		validator.setVerifyApiUrl(verifyApiUrl);
		ReCaptchaResponse response = validator.validate(privateKey, FacesUtils.getRemoteAddr(context)/*request.getRemoteAddr()*/, submittedValue.getChallenge(), submittedValue.getResponse());
		if (response == null) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error validating reCAPTCHA", "We are unable to validate reCAPTCHA. See error log for further details."));
		}
		
		if (!response.isValid()) {
			String errorCode = response.getErrorCode();
			String errorTitle = resourceBundle.getString("validation-failed");
			String errorMessage = resourceBundle.getString(errorCode);
			
			if (errorTitle == null || errorTitle.isEmpty()) {
				errorTitle = "Validation failed.";
			}
			
			if (errorMessage == null || errorMessage.isEmpty()) {
				errorMessage = "Error code: " + errorCode;
			}
			
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, errorTitle, errorMessage));
		}
	}
}

/**
 * 
 */
package za.co.sindi.faces.validator;

import java.util.Objects;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


/**
 * @author Bienfait Sindi
 * @since 26 April 2014
 *
 */
@FacesValidator(ConfirmPasswordValidator.VALIDATOR_ID)
public class ConfirmPasswordValidator implements Validator {

	public static final String VALIDATOR_ID = "za.co.sindi.faces.validator.ConfirmPasswordValidator";
	private static final String MESSAGE_BUNDLE_NAME = "za.co.sindi.properties.Messages";
	
	/* (non-Javadoc)
	 * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		// TODO Auto-generated method stub
		UIInput confirmedOPasswordComponent = (UIInput) component.getAttributes().get("confirmPassword");
		if (!Objects.equals(value, confirmedOPasswordComponent.getSubmittedValue())) {
			confirmedOPasswordComponent.setValid(false);
			ResourceBundle resourceBundle = ResourceBundle.getBundle(MESSAGE_BUNDLE_NAME /*context.getApplication().getMessageBundle()*/);
			throw new ValidatorException(new FacesMessage(resourceBundle.getString(VALIDATOR_ID + ".MESSAGE")));
		}
	}
}

/**
 * 
 */
package za.co.sindi.faces.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import za.co.sindi.common.utils.Strings;

/**
 * Converts any captured {@link String} to uppercase.
 * 
 * @author Bienfait Sindi
 * @since 21 March 2014
 *
 */
@FacesConverter("za.co.sindi.faces.converter.ToUpperCaseStringConverter")
public class ToUpperCaseStringConverter implements Converter {

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		// TODO Auto-generated method stub
		return value;
	}

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		// TODO Auto-generated method stub
		if (value == null) {
			return null;
		}
		
		String stringValue = value.toString();
		if (!Strings.isAllUpperCase(stringValue)) {
			return stringValue.toUpperCase();
		}
		
		return stringValue;
//		return (value != null) ? value.toString().toUpperCase() : null;
	}
}

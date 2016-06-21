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
 * Capitalize a given String.
 * 
 * @author Bienfait Sindi
 * @since 21 March 2014
 *
 */
@FacesConverter("za.co.sindi.faces.converter.CapitalizeConverter")
public class CapitalizeStringConverter implements Converter {

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
		
		return Strings.capitalize(value.toString());
	}
}

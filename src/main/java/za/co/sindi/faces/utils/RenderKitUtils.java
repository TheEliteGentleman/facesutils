/**
 * 
 */
package za.co.sindi.faces.utils;

import javax.faces.component.UIComponent;

/**
 * @author Bienfait Sindi
 * @since 07 April 2014
 *
 */
public class RenderKitUtils {

	private RenderKitUtils() {}
	
	public static boolean isComponentDisabled(UIComponent component) {
		Object value = component.getAttributes().get("disabled");
		return Boolean.valueOf(String.valueOf(value));
	}

	public static boolean isComponentReadOnly(UIComponent component) {
		Object value = component.getAttributes().get("readonly");
		return Boolean.valueOf(String.valueOf(value));
	}
	
	public static boolean isComponentDisabledOrReadonly(UIComponent component) {
		return isComponentDisabled(component) || isComponentReadOnly(component);
	}
}

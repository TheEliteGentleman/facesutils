/**
 * 
 */
package za.co.sindi.faces.component;

import javax.faces.component.UIComponentBase;

/**
 * The base component for any extra component that wishes to be registered under the Extra family.
 * 
 * @author Bienfait Sindi
 * @since 14 August 2014
 *
 */
public abstract class UIExtra extends UIComponentBase {

	public static final String COMPONENT_FAMILY = "za.co.sindi.faces.Extra";
	
	/**
	 * 
	 */
	public UIExtra() {
		super();
		// TODO Auto-generated constructor stub
		setRendererType(null);
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	@Override
	public String getFamily() {
		// TODO Auto-generated method stub
		return COMPONENT_FAMILY;
	}
}

/**
 * 
 */
package za.co.sindi.faces.component;

import javax.faces.component.FacesComponent;

import za.co.sindi.faces.renderer.IEConditionalCommentRenderer;

/**
 * The IE Conditional comment component.
 * This follows the Microsoft <a href="http://msdn.microsoft.com/en-us/library/ms537512%28v=vs.85%29.aspx">About Conditional comments</a> documentation.
 * 
 * @author Bienfait Sindi
 * @since 14 August 2014
 *
 */
@FacesComponent(IEConditionalComment.COMPONENT_TYPE)
public class IEConditionalComment extends UIExtra {

	public static final String COMPONENT_TYPE = "za.co.sindi.faces.IEConditionalComment";
	
	/**
	 * 
	 */
	public IEConditionalComment() {
		super();
		// TODO Auto-generated constructor stub
		setRendererType(IEConditionalCommentRenderer.RENDERER_TYPE);
	}

	private enum PropertyKeys {
		expression,
		downlevelHidden
	}
	
	/**
	 * @return the expression
	 */
	public String getExpression() {
		return (String) getStateHelper().eval(PropertyKeys.expression);
	}
	
	/**
	 * @param expression the expression to set
	 */
	public void setExpression(String expression) {
		getStateHelper().put(PropertyKeys.expression, expression);
	}
	
	/**
	 * @return the downlevelHidden
	 */
	public boolean isDownlevelHidden() {
		return (Boolean) getStateHelper().eval(PropertyKeys.downlevelHidden, true);
	}
	
	/**
	 * @param downlevelHidden the downlevelHidden to set
	 */
	public void setDownlevelHidden(boolean downlevelHidden) {
		getStateHelper().put(PropertyKeys.downlevelHidden, downlevelHidden);
	}	
}

/**
 * 
 */
package za.co.sindi.faces.renderer;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import za.co.sindi.faces.component.UIExtra;

/**
 * @author Bienfait Sindi
 * @since 14 August 2014
 *
 */
@FacesRenderer(componentFamily=UIExtra.COMPONENT_FAMILY, rendererType=IEConditionalCommentRenderer.RENDERER_TYPE)
public class IEConditionalCommentRenderer extends BaseRenderer {

	/** The standard renderer type. */
	public static final String RENDERER_TYPE = "za.co.sindi.faces.IEConditionalComment";

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeChildren(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	@Override
	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		// TODO Auto-generated method stub
		if (context == null) {
			throw new IllegalArgumentException("A FacesContext is required.");
		}
		
		if (component == null) {
			throw new IllegalArgumentException("A UIComponent is required.");
		}
		
		String expression = (String) component.getAttributes().get("expression");
		if (expression == null || expression.isEmpty()) {
			throw new IllegalArgumentException("No conditional comment expression was provided.");
		}
		
		boolean downlevelHidden = (Boolean) component.getAttributes().get("downlevelHidden");
		
		final ResponseWriter writer = context.getResponseWriter();
		writer.write("\n\t");
		writer.write("<!");
		if (downlevelHidden) {
			writer.write("--");
		}
		writer.write("[if ");
		writer.write(expression);
		writer.write("]>");
		super.encodeChildren(context, component);
		writer.write("\n\t");
		writer.write("<![endif]");
		if (downlevelHidden) {
			writer.write("--");
		}
		writer.write(">");
		writer.write("\n");
	}

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#getRendersChildren()
	 */
	@Override
	public boolean getRendersChildren() {
		// TODO Auto-generated method stub
		return true;
	}
}

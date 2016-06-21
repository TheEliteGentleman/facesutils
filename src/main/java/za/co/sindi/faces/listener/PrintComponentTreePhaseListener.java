/**
 * 
 */
package za.co.sindi.faces.listener;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 * @author Bienfait Sindi
 * @since 22 December 2015
 *
 */
public class PrintComponentTreePhaseListener implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6517930153734931589L;
	
	private static final Logger LOGGER = Logger.getLogger(PrintComponentTreePhaseListener.class.getName());
	private static final int INDENTATION_SIZE = 4;
	
	/* (non-Javadoc)
	 * @see javax.faces.event.PhaseListener#afterPhase(javax.faces.event.PhaseEvent)
	 */
	@Override
	public void afterPhase(PhaseEvent event) {
		// TODO Auto-generated method stub
		StringBuffer componentTree = new StringBuffer();
		generateComponentTree(componentTree, FacesContext.getCurrentInstance().getViewRoot(), 0);
//		System.out.println(componentTree.toString());
		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.log(Level.INFO, System.lineSeparator() + componentTree.toString());
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.event.PhaseListener#beforePhase(javax.faces.event.PhaseEvent)
	 */
	@Override
	public void beforePhase(PhaseEvent event) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.faces.event.PhaseListener#getPhaseId()
	 */
	@Override
	public PhaseId getPhaseId() {
		// TODO Auto-generated method stub
		return PhaseId.RENDER_RESPONSE;
	}
	
	/**
	 * Note, this is a recursive operation....
	 * 
	 * @param componentTree
	 * @param component
	 * @param depth
	 */
	private void generateComponentTree(StringBuffer componentTree, UIComponent component, int depth) {
		printIndentation(componentTree, depth);
		if (depth > 0) {
//			componentTree.append("|");
			componentTree.append(System.lineSeparator());
			printIndentation(componentTree, depth);
//			componentTree.append("|_");
			componentTree.append("_");
		}
		printComponentInfo(componentTree, component);
		componentTree.append(System.lineSeparator());
		
		int childCount = component.getChildCount();
		
		if (component != null && childCount > 0) {
			List<UIComponent> childrenComponents = component.getChildren();
			for (UIComponent childComponent : childrenComponents) {
				generateComponentTree(componentTree, childComponent, depth + 1);
			}
		}
	}
	
	private void printComponentInfo(StringBuffer componentTree, UIComponent component) {
		if (component != null) {
			componentTree.append(component.getClass().getSimpleName() + " [");
			componentTree.append("class=\"" + component.getClass().getName() + "\"");
			componentTree.append(", id=\"" + component.getId() + "\"");
			componentTree.append(", clientId=\"" + component.getClientId() + "\"");
			componentTree.append(", childCount=\"" + component.getChildCount() + "\"");
			componentTree.append(", facetCount=\"" + component.getFacetCount() + "\"");
			componentTree.append(", inView=\"" + String.valueOf(component.isInView()) + "\"");
			componentTree.append(", rendered=\"" + String.valueOf(component.isRendered()) + "\"");
			componentTree.append(", transient=\"" + String.valueOf(component.isTransient()) + "\"");
						
			if (component instanceof UIViewRoot) {
				UIViewRoot viewRoot = ((UIViewRoot)component);
				componentTree.append(", locale=\"" + viewRoot.getLocale() == null ? "" : viewRoot.getLocale().getLanguage() + "\"");
				componentTree.append(", renderKitId=\"" + viewRoot.getRenderKitId() + "\"");
				componentTree.append(", viewId=\"" + viewRoot.getViewId() + "\"");
			}
			
//			if (component instanceof ValueHolder) {
//				ValueHolder valueHolder = (ValueHolder)component;
//				if (valueHolder.getConverter() != null)
//					componentTree.append(", converter=\"" + valueHolder.getConverter().getClass().getName() + "\"");
//				
//				if (valueHolder.getLocalValue() != null)
//					componentTree.append(", localValue=\"" + valueHolder.getLocalValue() == null ? "null" : String.valueOf(valueHolder.getLocalValue()) + "\"");
//				
//				if (valueHolder.getValue() != null)	
//					componentTree.append(", value=\"" + valueHolder.getValue() == null ? "null" : String.valueOf(valueHolder.getValue()) + "\"");
//			}
			
			if (component instanceof EditableValueHolder) {
				EditableValueHolder valueHolder = (EditableValueHolder)component;
				componentTree.append(", localValueSet=\"" + String.valueOf(valueHolder.isLocalValueSet()) + "\"");
				componentTree.append(", required=\"" + String.valueOf(valueHolder.isRequired()) + "\"");
//				if (valueHolder.getSubmittedValue() != null)
//					componentTree.append(", submittedValue=\"" + String.valueOf(valueHolder.getSubmittedValue()) + "\"");
				componentTree.append(", valid=\"" + String.valueOf(valueHolder.isValid()) + "\"");
			}
			
			componentTree.append("]");
		}
	}
	
	private void printIndentation(StringBuffer componentTree, int depth) {
		if (depth > 0) {
			for (int i = 0; i < (depth * INDENTATION_SIZE); i++) {
				componentTree.append(" ");
				if (i > 0 && (i + 1) % INDENTATION_SIZE == 0) {
					componentTree.append("|");
				}
			}
		}
	}
}

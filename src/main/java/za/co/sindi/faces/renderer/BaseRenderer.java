/**
 * 
 */
package za.co.sindi.faces.renderer;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import za.co.sindi.faces.utils.RenderKitUtils;

/**
 * The Base renderer for all renderers in this project.
 * <p />
 * Most of the code are a direct copy-paste of the code found on class
 * <code>com.sun.faces.renderkit.html_basic.HtmlBasicRenderer</code>.
 * 
 * @author Bienfait Sindi
 * @since 07 April 2014
 * 
 */
public abstract class BaseRenderer extends Renderer {

	protected final Logger LOGGER = Logger.getLogger(this.getClass().getName());

	protected void validateParameters(FacesContext context, UIComponent component, Class<? extends UIComponent> componentClass) {
		if (context == null) {
			throw new NullPointerException("FacesContext may not be null.");
		}
		
		if (component == null) {
			throw new NullPointerException("UIComponent may not be null.");
		}
		
		if (componentClass != null && !componentClass.isInstance(component)) {
			throw new IllegalArgumentException("UIComponent " + component.getClientId(context) + " is not an instanceof " + componentClass.getName());
		}
	}
	
	/**
     * <p>Render nested child components by invoking the encode methods
     * on those components, but only when the <code>rendered</code>
     * property is <code>true</code>.</p>
     *
     * @param context FacesContext for the current request
     * @param component the component to recursively encode
     *
     * @throws IOException if an error occurrs during the encode process
     */
    protected void encodeRecursive(FacesContext context, UIComponent component) throws IOException {

        // suppress rendering if "rendered" property on the component is
        // false.
        if (!component.isRendered()) {
            return;
        }

        // Render this component and its children recursively
        component.encodeBegin(context);
        if (component.getRendersChildren()) {
            component.encodeChildren(context);
        } else {
        	Iterator<UIComponent> kids = getChildren(component);
            while (kids.hasNext()) {
                UIComponent kid = kids.next();
                encodeRecursive(context, kid);
            }
        }
        component.encodeEnd(context);
    }

    /**
     * @param component <code>UIComponent</code> for which to extract children
     *
     * @return an Iterator over the children of the specified
     *  component, selecting only those that have a
     *  <code>rendered</code> property of <code>true</code>.
     */
    protected Iterator<UIComponent> getChildren(UIComponent component) {

        int childCount = component.getChildCount();
        if (childCount > 0) {
            return component.getChildren().iterator();
        } else {
            return Collections.<UIComponent>emptyList().iterator();
        }
    }
    
    /**
     * @param component Component from which to return a facet
     * @param name      Name of the desired facet
     *
     * @return the specified facet from the specified component, but
     *  <strong>only</strong> if its <code>rendered</code> property is
     *  set to <code>true</code>.
     */
    protected UIComponent getFacet(UIComponent component, String name) {

        UIComponent facet = null;
        if (component.getFacetCount() > 0) {
            facet = component.getFacet(name);
            if ((facet != null) && !facet.isRendered()) {
                facet = null;
            }
        }
        return (facet);

    }
    
	protected boolean shouldEncode(UIComponent component) {
		// suppress rendering if "rendered" property on the component is
		// false.
		boolean encode = component.isRendered();
		if (!encode) {
			if (LOGGER.isLoggable(Level.FINE)) {
				LOGGER.log(Level.FINE, "End encoding component {0} since rendered attribute is set to false", component.getId());
			}
		}

		return encode;
	}

	protected boolean shouldDecode(UIComponent component) {
		boolean decode = !RenderKitUtils.isComponentDisabledOrReadonly(component);

		if (!decode) {
			if (LOGGER.isLoggable(Level.FINE)) {
				LOGGER.log(Level.FINE, "No decoding necessary since the component {0} is disabled or read-only", component.getId());
			}
		}

		return decode;
	}

	protected boolean shouldEncodeChildren(UIComponent component) {
		 // suppress rendering if "rendered" property on the component is
		 // false.
		 boolean encodeChildren = component.isRendered();
		 if (!encodeChildren) {
			 if (LOGGER.isLoggable(Level.FINE)) {
				 LOGGER.log(Level.FINE, "Children of component {0} will not be encoded since this component's rendered attribute is false", component.getId());
		 	}
		 }
		 
		 return encodeChildren;		 
	}
	
	/**
     * @param component the component of interest
     *
     * @return true if this renderer should render an id attribute.
     */
    protected boolean shouldWriteIdAttribute(UIComponent component) {

        // By default we only write the id attribute if:
        //
        // - We have a non-auto-generated id, or...
        // - We have client behaviors.
        //
        // We assume that if client behaviors are present, they
        // may need access to the id (AjaxBehavior certainly does).

        String id;
        return (null != (id = component.getId()) &&
                    (!id.startsWith(UIViewRoot.UNIQUE_ID_PREFIX) ||
                        ((component instanceof ClientBehaviorHolder) &&
                          !((ClientBehaviorHolder)component).getClientBehaviors().isEmpty())));
    }
    
    protected String writeIdAttributeIfNecessary(FacesContext context, ResponseWriter writer, UIComponent component) {

    	String id = null;
    	if (shouldWriteIdAttribute(component)) {
    		try {
    			writer.writeAttribute("id", id = component.getClientId(context), "id");
    		} catch (IOException e) {
    			if (LOGGER.isLoggable(Level.WARNING)) {
    				LOGGER.log(Level.WARNING, "Can't write id attribute.", e);
    			}
    		}
    	}
    	
    	return id;
    }
    
    @SuppressWarnings("unchecked")
	protected Iterator<FacesMessage> getMessageIter(FacesContext context, String forComponent, UIComponent component) {

    	Iterator<FacesMessage> messageIter;
    	// Attempt to use the "for" attribute to locate 
    	// messages.  Three possible scenarios here:
    	// 1. valid "for" attribute - messages returned
    	//    for valid component identified by "for" expression.
    	// 2. zero length "for" expression - global errors
    	//    not associated with any component returned
    	// 3. no "for" expression - all messages returned.
    	if (null != forComponent) {
    		if (forComponent.length() == 0) {
    			messageIter = context.getMessages(null);
    		} else {
    			UIComponent result = getForComponent(context, forComponent, component);
    			if (result == null) {
    				messageIter = Collections.EMPTY_LIST.iterator();
    			} else {
    				messageIter = context.getMessages(result.getClientId(context));
    			}
    		}
    	} else {
    		messageIter = context.getMessages();
    	}
    	
    	return messageIter;
    }
    
    protected UIComponent getForComponent(FacesContext context, String forComponent, UIComponent component) {

    	if (null == forComponent || forComponent.length() == 0) {
    		return null;
    	}

    	UIComponent result = null;
    	UIComponent currentParent = component;
    	try {
    		// Check the naming container of the current 
    		// component for component identified by
    		// 'forComponent'
    		while (currentParent != null) {
    			// If the current component is a NamingContainer,
    			// see if it contains what we're looking for.
    			result = currentParent.findComponent(forComponent);
    			if (result != null) {
    				break;
    			}
    		
    			// if not, start checking further up in the view
    			currentParent = currentParent.getParent();
    		}

    		// no hit from above, scan for a NamingContainer
    		// that contains the component we're looking for from the root.    
    		if (result == null) {
    			result = findUIComponentBelow(context.getViewRoot(), forComponent);
    		}
    	} catch (Exception e) {
    		// ignore - log the warning
    	}
    	// log a message if we were unable to find the specified
    	// component (probably a misconfigured 'for' attribute
    	if (result == null) {
    		if (LOGGER.isLoggable(Level.WARNING)) {
    			LOGGER.warning("Component for '" + forComponent + "' cannot be found in view.");
    		}
    	}
    	
    	return result;
    }
    
    protected String augmentIdReference(String forValue, UIComponent fromComponent) {

    	int forSuffix = forValue.lastIndexOf(UIViewRoot.UNIQUE_ID_PREFIX);
    	if (forSuffix <= 0) {
    		// if the for-value doesn't already have a suffix present
    		String id = fromComponent.getId();
    		if (id != null) {
    			int idSuffix = id.lastIndexOf(UIViewRoot.UNIQUE_ID_PREFIX);
    			if (idSuffix > 0) {
    				// but the component's own id does have a suffix
    				if (LOGGER.isLoggable(Level.FINE)) {
    					LOGGER.fine("Augmenting for attribute with " + id.substring(idSuffix) + " suffix from Id attribute");
    				}
    				forValue += id.substring(idSuffix);
    			}
    		}
    	}
    	
    	return forValue;
    }

    private static UIComponent findUIComponentBelow(UIComponent startPoint, String forComponent) {

    	UIComponent retComp = null;
    	if (startPoint.getChildCount() > 0) {
    		List<UIComponent> children = startPoint.getChildren();
    		for (int i = 0, size = children.size(); i < size; i++) {
    			UIComponent comp = children.get(i);

    			if (comp instanceof NamingContainer) {
    				try {
    					retComp = comp.findComponent(forComponent);
    				} catch (IllegalArgumentException iae) {
    					continue;
    				}
    			}

    			if (retComp == null) {
    				if (comp.getChildCount() > 0) {
    					retComp = findUIComponentBelow(comp, forComponent);
    				}
    			}

    			if (retComp != null) {
    				break;
    			}
    		}
    	}
    	
    	return retComp;
    }
}

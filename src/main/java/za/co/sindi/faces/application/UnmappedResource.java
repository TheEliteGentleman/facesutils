/**
 * 
 */
package za.co.sindi.faces.application;

import javax.faces.application.Resource;
import javax.faces.application.ResourceWrapper;
import javax.faces.context.FacesContext;

import za.co.sindi.faces.utils.FacesUtils;

/**
 * @author Bienfait Sindi
 * @since 20 September 2014
 *
 */
public class UnmappedResource extends ResourceWrapper {

	private Resource wrapped;
	
	/**
	 * @param wrapped
	 */
	public UnmappedResource(Resource wrapped) {
		super();
		this.wrapped = wrapped;
	}

	/* (non-Javadoc)
	 * @see javax.faces.application.ResourceWrapper#getRequestPath()
	 */
	@Override
	public String getRequestPath() {
		// TODO Auto-generated method stub
		String path = getWrapped().getRequestPath();
		String mapping = FacesUtils.getMapping(FacesContext.getCurrentInstance());

		if (mapping.charAt(0) == '/') {
			return path.replaceFirst(mapping, "");
		}
		
		if (path.contains("?")) {
			return path.replace(mapping + "?", "?");
		}
		
		return path.substring(0, path.length() - mapping.length());
	}

	/* (non-Javadoc)
	 * @see javax.faces.application.ResourceWrapper#getWrapped()
	 */
	@Override
	public Resource getWrapped() {
		// TODO Auto-generated method stub
		return wrapped;
	}
}

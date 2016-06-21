/**
 * 
 */
package za.co.sindi.faces.application;

import java.io.IOException;
import java.util.Map.Entry;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import za.co.sindi.common.utils.IOUtils;
import za.co.sindi.common.utils.Strings;

/**
 * @author Bienfait Sindi
 * @since 20 September 2014
 *
 */
public class UnmappedResourceHandler extends ResourceHandlerWrapper {

//	private static final Logger LOGGER = Logger.getLogger(UnmappedResourceHandler.class.getName());
	private ResourceHandler wrapped;
	
	/**
	 * @param wrapped
	 */
	public UnmappedResourceHandler(ResourceHandler wrapped) {
		super();
		this.wrapped = wrapped;
	}

	/* (non-Javadoc)
	 * @see javax.faces.application.ResourceHandlerWrapper#createResource(java.lang.String)
	 */
	@Override
	public Resource createResource(String resourceName) {
		// TODO Auto-generated method stub
		Resource resource = super.createResource(resourceName);
		return (resource == null) ? null : new UnmappedResource(resource);
	}

	/* (non-Javadoc)
	 * @see javax.faces.application.ResourceHandlerWrapper#createResource(java.lang.String, java.lang.String)
	 */
	@Override
	public Resource createResource(String resourceName, String libraryName) {
		// TODO Auto-generated method stub
		Resource resource = super.createResource(resourceName, libraryName);
		return (resource == null) ? null : new UnmappedResource(resource);
	}

	/* (non-Javadoc)
	 * @see javax.faces.application.ResourceHandlerWrapper#createResource(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Resource createResource(String resourceName, String libraryName, String contentType) {
		// TODO Auto-generated method stub
		Resource resource = super.createResource(resourceName, libraryName, contentType);
		return (resource == null) ? null : new UnmappedResource(resource);
	}

	/* (non-Javadoc)
	 * @see javax.faces.application.ResourceHandlerWrapper#isResourceRequest(javax.faces.context.FacesContext)
	 */
	@Override
	public boolean isResourceRequest(FacesContext context) {
		// TODO Auto-generated method stub
//		return context.getExternalContext().getRequestServletPath().contains(ResourceHandler.RESOURCE_IDENTIFIER);
		return ResourceHandler.RESOURCE_IDENTIFIER.equals(context.getExternalContext().getRequestServletPath());
	}

	/* (non-Javadoc)
	 * @see javax.faces.application.ResourceHandlerWrapper#handleResourceRequest(javax.faces.context.FacesContext)
	 */
	@Override
	public void handleResourceRequest(FacesContext context) throws IOException {
		// TODO Auto-generated method stub
		ExternalContext externalContext = context.getExternalContext();
		
//		int resourceIndex = externalContext.getRequestServletPath().indexOf(ResourceHandler.RESOURCE_IDENTIFIER);
//		String resourceName = externalContext.getRequestServletPath().substring(resourceIndex + ResourceHandler.RESOURCE_IDENTIFIER.length() + 1);
		
		String pathInfo = externalContext.getRequestPathInfo();
		String resourceName = (pathInfo != null) ? pathInfo.substring(1) : "";
		
//		LOGGER.log(Level.INFO, "getServletPath() = " + externalContext.getRequestServletPath());
//		LOGGER.log(Level.INFO, "getPathInfo() = " + pathInfo);
		
		if (Strings.isNullOrEmpty(resourceName)) {
			externalContext.setResponseStatus(HttpServletResponse.SC_NOT_FOUND);
			return ;
		}
		
		String libraryName = externalContext.getRequestParameterMap().get("ln");
		if (Strings.isNullOrEmpty(libraryName)) {
			//Get from the referer.
			String referer = externalContext.getRequestHeaderMap().get("Referer");
//			LOGGER.log(Level.INFO, "Referer = " + referer);
			if (!Strings.isNullOrEmpty(referer)) {
//				//Get mapping
//				int queryStartIndex = referer.indexOf("?");
//				String extension = referer.substring(referer.lastIndexOf(".", queryStartIndex), queryStartIndex);
//				LOGGER.log(Level.INFO, "extension = " + extension);
//				if (!resourceName.contains(extension)) {
//					resourceName += "." + extension;
//				}
				
				//Get library name
				int libraryNameIndex = referer.indexOf("ln=");
				int querySeparatorIndex = referer.indexOf("&", libraryNameIndex + 1);
				if (libraryNameIndex > -1) {
					if (querySeparatorIndex > -1) {
						libraryName = referer.substring(libraryNameIndex + 3, querySeparatorIndex);
					} else {
						libraryName = referer.substring(libraryNameIndex + 3);
					}
				}
				
			}
		}
		
//		LOGGER.log(Level.INFO, "resourceName = " + resourceName);
//		LOGGER.log(Level.INFO, "libraryName = " + libraryName);
		
		Resource resource = null;
		if (!Strings.isNullOrEmpty(libraryName)) {
			resource = createResource(resourceName, libraryName);
		} else {
			resource = createResource(resourceName);
		}
		
		if (resource == null) {
			//externalContext.setResponseStatus(HttpServletResponse.SC_NOT_FOUND);
			//Revert back to the wrapper...
			super.handleResourceRequest(context);
			return ;
		}
		
		if (!resource.userAgentNeedsUpdate(context)) {
			externalContext.setResponseStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return;
		}

		externalContext.setResponseContentType(resource.getContentType());

		for (Entry<String, String> header : resource.getResponseHeaders().entrySet()) {
			externalContext.setResponseHeader(header.getKey(), header.getValue());
		}
		
		//Finally
		int length = IOUtils.fastCopy(resource.getInputStream(), externalContext.getResponseOutputStream());
		externalContext.setResponseContentLength(length);
	}

	/* (non-Javadoc)
	 * @see javax.faces.application.ResourceHandlerWrapper#getWrapped()
	 */
	@Override
	public ResourceHandler getWrapped() {
		// TODO Auto-generated method stub
		return wrapped;
	}
}

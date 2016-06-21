/**
 * 
 */
package za.co.sindi.faces.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import za.co.sindi.common.utils.PreConditions;
import za.co.sindi.common.utils.IOUtils;
import za.co.sindi.common.utils.Strings;

/**
 * <i>Note:</i> certain portions of this source code is taken from OmniFaces <a href="https://github.com/omnifaces/omnifaces/blob/2.0/src/main/java/org/omnifaces/util/FacesLocal.java">FacesLocal</a>.
 * 
 * @author Bienfait Sindi
 * @since 06 December 2013
 *
 */
public final class FacesUtils {

	private static final int DEFAULT_SENDFILE_BUFFER_SIZE = 10240;
	private static final String SENDFILE_HEADER = "%s;filename=\"%2$s\"; filename*=UTF-8''%2$s";
	
	private FacesUtils() {
		//TODO: Absolutely nothing
	}
	
	private static void ensureContext(FacesContext context) {
		PreConditions.checkArgument(context != null, "A FacesContext is required.");
	}
	
	public static void addResponseCookie(FacesContext context, String name, String value, String comment, String domain, String path, int maxAge) {
		addResponseCookie(context, name, value, comment, domain, path, maxAge, false);
	}
	
	public static void addResponseCookie(FacesContext context, String name, String value, String comment, String domain, String path, int maxAge, boolean httpOnly) {
		ensureContext(context);
		
		ExternalContext externalContext = context.getExternalContext();
		Map<String, Object> properties = new HashMap<String, Object>();
		
		if (!Strings.isNull(comment)) {
			properties.put("comment", comment);
		}
		
		if (!Strings.isNull(domain)) {
			properties.put("domain", domain);
		}
		
		if (!Strings.isNull(path)) {
			properties.put("path", path);
		}
		
		properties.put("maxAge", maxAge);
		properties.put("httpOnly", httpOnly);
		properties.put("secure", getRequest(context).isSecure());
		
		externalContext.addResponseCookie(name, value, properties);
	}
	
	public static void removeResponseCookie(FacesContext context, String name, String path) {
		addResponseCookie(context, name, null, null, null, path, 0);
	}
	
	public static Locale getLocale(FacesContext context) {
		ensureContext(context);
		Locale locale = null;
		UIViewRoot viewRoot = context.getViewRoot();
		// Prefer the locale set in the view.
		if (viewRoot != null) {
			locale = viewRoot.getLocale();
		}
		
		// Then the client preferred locale.
		if (locale == null) {
			Locale clientLocale = context.getExternalContext().getRequestLocale();
			if (getSupportedLocales(context).contains(clientLocale)) {
				locale = clientLocale;
			}
		}
		
		// Then the JSF default locale.
		if (locale == null) {
			locale = context.getApplication().getDefaultLocale();
		}
		
		// Finally the system default locale.
		if (locale == null) {
			locale = Locale.getDefault();
		}
		
		return locale;
	}
	
	public static Locale getDefaultLocale(FacesContext context) {
		ensureContext(context);
		return context.getApplication().getDefaultLocale();
	}
	
	public static List<Locale> getSupportedLocales(FacesContext context) {
		Application application = context.getApplication();
		List<Locale> supportedLocales = new ArrayList<>();
		Locale defaultLocale = application.getDefaultLocale();
		
		if (defaultLocale != null) {
			supportedLocales.add(defaultLocale);
		}
		
		for (Iterator<Locale> iter = application.getSupportedLocales(); iter.hasNext();) {
			Locale supportedLocale = iter.next();
			if (!supportedLocale.equals(defaultLocale)) {
				supportedLocales.add(supportedLocale);
			}
		}
		
		return supportedLocales;
	}
	
	public static void setLocale(FacesContext context, Locale locale) {
		ensureContext(context);
		PreConditions.checkArgument(locale != null, "No locale specified.");
		
		UIViewRoot viewRoot = context.getViewRoot();
		PreConditions.checkState(viewRoot != null, "No view exists.");
		viewRoot.setLocale(locale);
	}
	
	public static Map<String, Object> getApplicationMap(FacesContext context) {
		ensureContext(context);
		return context.getExternalContext().getApplicationMap();
	}
	
	public static void removeApplicationAttribute(FacesContext context, String name) {
		getApplicationMap(context).remove(name);
	}
	
	public static HttpServletRequest getRequest(FacesContext context) {
		ensureContext(context);
		return (HttpServletRequest) context.getExternalContext().getRequest();
	}
	
	public static Map<String, Object> getRequestMap(FacesContext context) {
		ensureContext(context);
		return context.getExternalContext().getRequestMap();
	}
	
	public static boolean containsRequestAttribute(FacesContext context, String name) {
		return getRequestMap(context).containsKey(name);
	}
	
	public static Object getRequestAttribute(FacesContext context, String name) {
		return getRequestMap(context).get(name);
	}
	
	public static void removeRequestAttribute(FacesContext context, String name) {
		getRequestMap(context).remove(name);
	}
	
	public static void setRequestAttribute(FacesContext context, String name, Object value) {
		getRequestMap(context).put(name, value);
	}
	
	public static Map<String, String> getRequestParameterMap(FacesContext context) {
		ensureContext(context);
		return context.getExternalContext().getRequestParameterMap();
	}
	
	public static boolean containsRequestParameter(FacesContext context, String name) {
		return getRequestParameterMap(context).containsKey(name);
	}
	
	public static String getRequestParameter(FacesContext context, String name) {
		Map<String, String> requestParameterMap = getRequestParameterMap(context);
		if (requestParameterMap == null) {
			return null;
		}
		
		return requestParameterMap.get(name);
	}
	
	public static HttpServletResponse getResponse(FacesContext context) {
		ensureContext(context);
		return (HttpServletResponse) context.getExternalContext().getResponse();
	}
	
	public static HttpSession getSession(FacesContext context) {
		return getSession(context, false);
	}
	
	public static HttpSession getSession(FacesContext context, boolean create) {
		ensureContext(context);
		return (HttpSession) context.getExternalContext().getSession(create);
	}
	
	public static String getSessionId(FacesContext context, boolean create) {
		HttpSession session = getSession(context);
		return (session == null) ? null : session.getId();
	}
	
	public static Map<String, Object> getSessionMap(FacesContext context) {
		ensureContext(context);
		return context.getExternalContext().getSessionMap();
	}
	
	public static boolean containsSessionAttribute(FacesContext context, String name) {
		return getSessionMap(context).containsKey(name);
	}
	
	public static Object getSessionAttribute(FacesContext context, String name) {
		return getSessionMap(context).get(name);
	}
	
	public static void invalidateSession(FacesContext context) {
		ensureContext(context);
		context.getExternalContext().invalidateSession();
	}
	
	public static void removeSessionAttribute(FacesContext context, String name) {
		getSessionMap(context).remove(name);
	}
	
	public static void setSessionAttribute(FacesContext context, String name, Object value) {
		getSessionMap(context).put(name, value);
	}
	
	public static String getRequestContextPath(FacesContext context) {
		ensureContext(context);
		return context.getExternalContext().getRequestContextPath();
	}
	
	public static Map<String, Object> getRequestCookieMap(FacesContext context) {
		ensureContext(context);
		return context.getExternalContext().getRequestCookieMap();
	}
	
	public static Cookie getCookie(FacesContext context, String cookieName) {
		Map<String, Object> cookieMap = getRequestCookieMap(context);
		Cookie cookie = null;
		
		if (cookieMap != null) {
			cookie = (Cookie) cookieMap.get(cookieName);
		}
		
		return cookie;
	}
	
	public static String getCookieValue(FacesContext context, String cookieName) {
		Cookie cookie = getCookie(context, cookieName);
		return cookie == null ? null : cookie.getValue();
	}
	
	public static Map<String, String> getRequestHeaderMap(FacesContext context) {
		ensureContext(context);
		return context.getExternalContext().getRequestHeaderMap();
	}
	
	public static String getRequestHeader(FacesContext context, String headerName) {
		return getRequestHeaderMap(context).get(headerName);
	}
	
	public static String getRemoteAddr(FacesContext context) {
		String forwardedFor = getRequestHeader(context, "X-Forwarded-For");

		if (!Strings.isNullOrEmpty(forwardedFor)) {
			return forwardedFor.split("\\s*,\\s*", 2)[0]; // It's a comma separated string: client,proxy1,proxy2,...
		}

		return getRequest(context).getRemoteAddr();
	}
	
	public static String getRemoteHost(FacesContext context) {
//		ensureContext(context);
		String forwardedHost = getRequestHeader(context, "X-Forwarded-Host");

		if (!Strings.isNullOrEmpty(forwardedHost)) {
			return forwardedHost;
		}
		
		return getRequest(context).getRemoteHost();
	}
	
	public static ResourceBundle getResourceBundle(FacesContext context, String name) {
		ensureContext(context);
		return context.getApplication().getResourceBundle(context, name);
	}
	
	public static void redirect(FacesContext context, String url) throws IOException {
		ensureContext(context);
		
		ExternalContext externalContext = context.getExternalContext();
		externalContext.getFlash().setRedirect(true);
		externalContext.redirect(url);
	}
	
	public static String getMapping(FacesContext context) {
		ensureContext(context);

		ExternalContext externalContext = context.getExternalContext();
		String path = externalContext.getRequestServletPath();
		if (externalContext.getRequestPathInfo() == null) {
			return path.substring(path.lastIndexOf('.'));
		}
		
		return path;
	}
	
	public static void downloadBytes(FacesContext context, byte[] bytes, String fileName, String contentType, boolean downloadAsAttachment) throws IOException {
		PreConditions.checkArgument(bytes != null, "No bytes were specified.");
		downloadStream(context, new ByteArrayInputStream(bytes), fileName, contentType, bytes.length, downloadAsAttachment);
	}
	
	public static void downloadFile(FacesContext context, File file, boolean downloadAsAttachment) throws IOException {
		PreConditions.checkArgument(file != null, "No file was specified.");
		downloadStream(context, new FileInputStream(file), file.getName(), Files.probeContentType(file.toPath()), file.length(), downloadAsAttachment);
	}
	
	public static void downloadStream(FacesContext context, InputStream input, String fileName, String contentType, long contentLength, boolean downloadAsAttachment) throws IOException {
		ensureContext(context);
		PreConditions.checkArgument(!Strings.isNullOrEmpty(fileName), "No filename was specified.");
	
		ExternalContext externalContext = context.getExternalContext();
		// Prepare the response and set the necessary headers.
		externalContext.setResponseBufferSize(DEFAULT_SENDFILE_BUFFER_SIZE);
		if (!Strings.isNullOrEmpty(contentType)) {
			externalContext.setResponseContentType(contentType);
		}
		externalContext.setResponseHeader("Content-Disposition", String.format(SENDFILE_HEADER, (downloadAsAttachment ? "attachment" : "inline"), URLEncoder.encode(fileName, "UTF-8")));
		
		// Not exactly mandatory, but this fixes at least a MSIE quirk: http://support.microsoft.com/kb/316431
		if (!isPortletRequest(context) && ((HttpServletRequest) externalContext.getRequest()).isSecure()) {
			externalContext.setResponseHeader("Cache-Control", "public");
			externalContext.setResponseHeader("Pragma", "public");
		}
		
		// If content length is known, set it. Note that setResponseContentLength() cannot be used as it takes only int.
		if (contentLength != -1) {
			externalContext.setResponseHeader("Content-Length", String.valueOf(contentLength));
		}
		
		long size = IOUtils.fastCopy(input, externalContext.getResponseOutputStream());
		
		// This may be on time for files smaller than the default buffer size, but is otherwise ignored anyway.
		if (contentLength == -1) {
			externalContext.setResponseHeader("Content-Length", String.valueOf(size));
		}
		
		context.responseComplete();
	}
	
	public static boolean isPortletRequest(FacesContext context) {
		ensureContext(context);
		Map<String, Object> requestMap = getRequestMap(context);
		return requestMap.containsKey("javax.portlet.faces.phase") || requestMap.get("javax.portlet.faces.phase") != null;
	}
}

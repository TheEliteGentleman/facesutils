/**
 * 
 */
package za.co.sindi.faces.renderer;

import java.io.IOException;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import za.co.sindi.common.utils.Strings;
import za.co.sindi.faces.component.InputReCaptcha;
import za.co.sindi.faces.value.SubmittedReCaptchaValue;
import za.co.sindi.recaptcha.Constants;

/**
 * @author Bienfait Sindi
 * @since 30 March 2014
 *
 */
@FacesRenderer(componentFamily = UIInput.COMPONENT_FAMILY, rendererType = InputReCaptcha.RENDERER_TYPE)
public class InputReCaptchaRenderer extends BaseRenderer {

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#decode(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	@Override
	public void decode(FacesContext context, UIComponent component) {
		// TODO Auto-generated method stub
		super.decode(context, component);
		InputReCaptcha reCaptcha = (InputReCaptcha) component;
		Map<String, String> requestMap = context.getExternalContext().getRequestParameterMap();
		
		String challenge = requestMap.get(Constants.RECAPTCHA_CHALLENGE_FIELD);
		String response = requestMap.get(Constants.RECAPTCHA_RESPONSE_FIELD);
		
		if (Strings.isNull(challenge)) {
			challenge = "";
		}
		
		reCaptcha.setSubmittedValue(new SubmittedReCaptchaValue(challenge, response));
	}

	/* (non-Javadoc)
	 * @see javax.faces.render.Renderer#encodeEnd(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		// TODO Auto-generated method stub
		super.encodeEnd(context, component);
		
		if (!shouldEncode(component)) {
			return ;
		}
		
		InputReCaptcha reCaptcha = (InputReCaptcha) component;
		String publicKey = reCaptcha.getPublicKey();
		if (publicKey == null || publicKey.isEmpty()) {
			throw new FacesException("reCAPTCHA API requires a public key.");
		}
		String protocol = reCaptcha.isSecure() || context.getExternalContext().isSecure() ? "https" : "http";
		final ResponseWriter writer = context.getResponseWriter();
		
		//RecaptchaOptions
		writer.startElement("script", component);
		writer.writeAttribute("type", "text/javascript", null);
		writer.write("var RecaptchaOptions = {");
		writer.write("theme: '" + reCaptcha.getTheme() + "'");
		if (!"en".equals(reCaptcha.getLang())) {
			writer.write(",lang: '" + reCaptcha.getLang() + "'");
		}
		
		if (reCaptcha.getTabindex() > 0) {
			writer.write(",tabindex: " + reCaptcha.getLang());
		}
		writer.write("};");
		writer.endElement("script");
		
		writer.startElement("script", component);
		writer.writeAttribute("type", "text/javascript", null);
		writer.writeAttribute("src", protocol + "://" + Constants.RECAPTCHA_API_SERVER_URL + "/challenge?k=" + publicKey, null);
		writer.endElement("script");
		
		writer.startElement("noscript", component);
		
		writer.startElement("iframe", component);
		writer.writeAttribute("src", protocol + "://" + Constants.RECAPTCHA_API_SERVER_URL + "/noscript?k=" + publicKey, null);
		writer.writeAttribute("height", "300", null);
		writer.writeAttribute("width", "500", null);
		writer.writeAttribute("frameborder", "0", null);
		writer.endElement("iframe");
		writer.write("<br />");
		
		writer.startElement("textarea", component);
		writer.writeAttribute("id", Constants.RECAPTCHA_CHALLENGE_FIELD, null);
		writer.writeAttribute("name", Constants.RECAPTCHA_CHALLENGE_FIELD, null);
		writer.writeAttribute("rows", "3", null);
		writer.writeAttribute("cols", "40", null);
		writer.endElement("textarea");
		
		writer.startElement("input", component);
		writer.writeAttribute("type", "hidden", null);
		writer.writeAttribute("id", Constants.RECAPTCHA_RESPONSE_FIELD, null);
		writer.writeAttribute("name", Constants.RECAPTCHA_RESPONSE_FIELD, null);
		writer.writeAttribute("value", Constants.MANUAL_CHALLENGE, null);
		writer.endElement("input");
		
		writer.endElement("noscript");
	}
}

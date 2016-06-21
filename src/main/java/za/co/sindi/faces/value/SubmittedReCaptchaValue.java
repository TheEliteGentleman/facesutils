/**
 * 
 */
package za.co.sindi.faces.value;

import java.io.Serializable;

/**
 * @author Bienfait Sindi
 * @since 26 April 2014
 *
 */
public class SubmittedReCaptchaValue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -239534452051034637L;
	private String challenge;
	private String response;
	
	/**
	 * @param challenge
	 * @param response
	 */
	public SubmittedReCaptchaValue(String challenge, String response) {
		super();
		this.challenge = challenge;
		this.response = response;
	}

	/**
	 * @return the challenge
	 */
	public String getChallenge() {
		return challenge;
	}

	/**
	 * @return the response
	 */
	public String getResponse() {
		return response;
	}
}

/**
 * 
 */
package za.co.sindi.faces.function;

/**
 * @author Bienfait Sindi
 * @since 28 March 2014
 *
 */
public final class Functions {

	private Functions() {
		//TODO: Nothing...
	}
	
	public static String concat(final String str1, final String str2) {
		return (str1 == null ? "" : str1).concat(str2 == null ? "" : str2);
	}
}

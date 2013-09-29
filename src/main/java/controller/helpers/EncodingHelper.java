package controller.helpers;

/**
 * This class converts strings from one encoding to another
 * @author Yalchyk Ilya
 */
public class EncodingHelper {

	/**
	 * Converts string from UTF-8 to internal Java String format (ISO-8859-1)
	 * If convert has been failed, then returns null
	 * @param s string in UTF-8
	 * @return string in ISO-8859-1 or null
	 */
    public static String convertFromUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("ISO-8859-1"), "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }
 
    /**
	 * Converts string from internal Java String format to UTF-8
	 * If convert has been failed, then returns null
	 * @param s string in ISO-8859-1
	 * @return string in UTF-8 or null
	 */
    public static String convertToUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }
}

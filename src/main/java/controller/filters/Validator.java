package controller.filters;

import model.Contact;
import model.Phone;

public class Validator {
	
	public static boolean isNumber(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public static boolean isNonNegativeNumber(String str) {
		int number;
		try {
			number = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return !isNegativeNumber(number);
	}
	
	public static boolean isPositiveNumber(String str) {
		int number;
		try {
			number = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return isPositiveNumber(number);
	}
	
	public static boolean isNegativeNumber(int number) {
		return number < 0 ? true : false;
	}
	
	public static boolean isPositiveNumber(int number) {
		return number > 0 ? true : false;
	}
	
	public static boolean isEnglishName(String str) {
		return str.matches("[a-zA-z]+([ '-][a-zA-Z]+)*");
	}
	
	public static boolean isRussianName(String str) {
		return str.matches("(\\p{L})+([ '-](\\p{L})+)*");
	}
	
	public static boolean isName(String str) {
		return isRussianName(str) || isEnglishName(str);
	}
	
	public static boolean isBoolean(String str) {
		return str.matches("(0|1)");
	}
	
	public static boolean isSex(String str) {
		try {
			Contact.Sex.valueOf(str.toUpperCase());
		} catch (IllegalArgumentException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}
	
	public static boolean isMartialStatus(String str) {
		try {
			Contact.MartialStatus.valueOf(str.toUpperCase());
		} catch (IllegalArgumentException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}
	
	public static boolean isPhoneType(String str) {
		try {
			Phone.PhoneType.valueOf(str.toUpperCase());
		} catch (IllegalArgumentException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}
	
	public static boolean isDate(String str) {
		return str.matches("\\d{4}-[01]\\d-[0-3]\\d");
	}
	
	public static boolean isURL(String str) {
		return str.matches("^http(s{0,1})://[a-zA-Z0-9_/\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9_/\\&\\?\\=\\-\\.\\~\\%]*");
	}
	
	public static boolean isEmail(String str) {
		return str.matches("^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})");
	}
	
	public static boolean isEmails(String str) {
		String[] emails = str.split(";");
		for (int i = 0; i < emails.length; i++) {
			if (!isEmail(emails[i])) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isComparisonSign(String str) {
		return str.matches("(>|<|>=|<=|=)");
	}
	
	public static boolean isUploadPath(String str) {
		// TODO: better validation
		return true;
	}
}

package om.gov.moh.eab.utils;

import java.io.UnsupportedEncodingException;

public class ArabicConversion {
	public static String getArabicEncodedString(String arg) {
		try {
			if (arg == null) {
				return "";
			}
			return new String(arg.getBytes("iso8859-1"), "windows-1256");
		} catch (UnsupportedEncodingException e) {
		} catch (Exception e) {
			// TODO, handle exception
		}
		return null;
	}

	public static String setArabicEncodedString(String arg) {
		try {
			if (arg == null) {
				return "";
			}

			return new String(arg.getBytes("windows-1256"), "iso8859-1");
		} catch (UnsupportedEncodingException e) {
		} catch (Exception e) {
			// TODO, handle exception
		}
		return null;
	}

	

}

package no.hist.aitel.android.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class FmtUtil {

    public static boolean isNumeric(String s) {
        return s.matches("^\\d+$");
    }

    public static boolean isAlphaNumeric(String s) {
        return s.matches("^[\\w\\s]+$");
    }

    public static String encodeUrl(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

}

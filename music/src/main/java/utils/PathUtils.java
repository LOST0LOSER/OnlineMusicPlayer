package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathUtils {
    static Pattern pattern = null;
    static Matcher matcher;
    static {
        pattern = Pattern.compile("\"(.*?)\"");
    }
    public static String correctString(String inputString){
        return inputString.substring(inputString.indexOf('\"')+1,inputString.lastIndexOf('\"'));
    }
}

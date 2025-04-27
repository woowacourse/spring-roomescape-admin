package roomescape.util;

import java.util.regex.Pattern;

public class FormatValidator {
    public static void validateDateFormat(String date) {
        String dateFormatRegex = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
        if(Pattern.matches(dateFormatRegex, date)){
            return;
        }
        throw new IllegalArgumentException("Invalid date format");
    }

    public static void validateTimeFormat(String date) {
        String dateFormatRegex = "^([01]\\d|2[0-3]):[0-5]\\d$";
        if(Pattern.matches(dateFormatRegex, date)){
            return;
        }
        throw new IllegalArgumentException("Invalid time format");
    }

    public static void validateNameFormat(String name) {
        String nameFormatRegex = "^([가-힣]{2,5}|[a-zA-Z]{2,30})$";
        if(Pattern.matches(nameFormatRegex, name)){
            return;
        }
        throw new IllegalArgumentException("Invalid name format");
    }
}

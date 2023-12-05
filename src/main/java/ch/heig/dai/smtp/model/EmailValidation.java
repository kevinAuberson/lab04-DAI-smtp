package ch.heig.dai.smtp.model;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * EmailValidation class represents a simple email validation.
 * @author Kevin Auberson, Adrian Rogner
 */
public class EmailValidation {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*" +
            "@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * Returns true if the email is valid, false otherwise.
     *
     * @param email The email to be validated.
     * @return True if the email is valid, false otherwise.
     */
    public static boolean isValid(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
}
package tech.theraven.custumerhub.utility;

public final class RegexPatternConstants {

    public static final String FULL_NAME_PATTERN = "^[a-zA-Z\\s]{2,50}$";
    public static final String PHONE_PATTERN = "^\\+\\d{6,14}$|^$";
    public static final String EMAIL_PATTERN = "^[^@]{1,98}@[a-zA-Z0-9.-]+$";

    private RegexPatternConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

}

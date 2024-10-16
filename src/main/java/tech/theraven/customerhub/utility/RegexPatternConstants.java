package tech.theraven.customerhub.utility;

public final class RegexPatternConstants {

    public static final String FULL_NAME_PATTERN = "^[a-zA-Z\\s]{2,50}$";
    public static final String PHONE_PATTERN = "^\\+\\d{6,14}$|^$";
    public static final String EMAIL_PATTERN = "^(?=.{1,100}$)" // Ensure the entire email length is between 1 and 100 characters 
                                               + "^[A-Za-z0-9_-]+" // Local part: Start with alphanumeric characters, underscores or dashes
                                               + "(\\.[A-Za-z0-9_-]+)*" // Local part: Allow dot-separated alphanumeric/underscore/dash sequences
                                               + "@[^-][A-Za-z0-9-]+" // Domain part: Domain part starts with alphanumeric characters and dashes (but not starting with a dash)
                                               + "(\\.[A-Za-z0-9-]+)*" // Domain part: Allow dot-separated domain parts
                                               + "(\\.[A-Za-z]{2,})$"; // Top-level domain: Ensure the domain ends with a valid top-level domain (minimum 2 characters)

    private RegexPatternConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

}

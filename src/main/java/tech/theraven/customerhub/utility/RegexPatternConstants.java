package tech.theraven.customerhub.utility;

public final class RegexPatternConstants {

    public static final String FULL_NAME_PATTERN = "^[a-zA-Z\\s]{2,50}$";
    public static final String PHONE_PATTERN = "^\\+\\d{6,14}$|^$";
    public static final String EMAIL_PATTERN = "^(?=.{1,100}$)" 
                                               + "(?=.{1,96}@.{3,})" 
                                               + "(?!.*\\.\\.)" 
                                               + "[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+" 
                                               + "(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*" 
                                               + "@"
                                               + "[A-Za-z0-9-]+" 
                                               + "(?:\\.[A-Za-z]{2,})$"; 

    private RegexPatternConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

}

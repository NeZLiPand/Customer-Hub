package tech.theraven.custumerhub.validator;

public interface DataValidator {

    boolean validateFullName(String fullName);

    boolean validateEmail(String email);

    boolean validateEmailIsUnique(String email);

    boolean validatePhone(String phone);

    boolean isCustomerDataValid(String fullName,
                                String email,
                                String phone);
    
}

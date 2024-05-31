package tech.theraven.custumerhub.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tech.theraven.custumerhub.model.repository.CustomerRepository;
import tech.theraven.custumerhub.utility.RegexPatternConstants;

@Component
public class DataValidatorImpl
    implements
        DataValidator {

    private final CustomerRepository customerRepository;

    @Autowired
    public DataValidatorImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public boolean validateFullName(String fullName) {
        return fullName.matches(RegexPatternConstants.FULL_NAME_PATTERN);
    }

    @Override
    public boolean validateEmail(String email) {
        return email != null
               && email.length() >= 2
               && email.length() <= 100
               && email.matches(RegexPatternConstants.EMAIL_PATTERN);
    }

    @Override
    public boolean validateEmailIsUnique(String email) {
        String foundEmail = customerRepository.findByEmail(email);
        return null == foundEmail
               || !foundEmail.equals(email);
    }

    @Override
    public boolean validatePhone(String phone) {
        return phone == null
               || phone.matches(RegexPatternConstants.PHONE_PATTERN);
    }

    @Override
    public boolean isCustomerDataValid(String fullName,
                                       String email,
                                       String phone) {
        return validateFullName(fullName)
               && validateEmail(email)
               && validateEmailIsUnique(email)
               && validatePhone(phone);
    }

}

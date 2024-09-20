package tech.theraven.customerhub.validator;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tech.theraven.customerhub.entity.CustomerEntity;
import tech.theraven.customerhub.repository.CustomerRepository;
import tech.theraven.customerhub.utility.RegexPatternConstants;

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
               && email.matches(RegexPatternConstants.EMAIL_PATTERN);
    }

    @Override
    public boolean validateEmailIsUnique(String email) {
        Optional<CustomerEntity> foundEntityByEmail = customerRepository.findByEmail(email);
        return foundEntityByEmail.isEmpty();
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

package tech.theraven.customerhub.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tech.theraven.customerhub.entity.CustomerEntity;
import tech.theraven.customerhub.repository.CustomerRepository;

import static org.mockito.Mockito.when;

public class DataValidatorImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private DataValidatorImpl dataValidator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidateFullName_shouldReturnTrue_whenValidFullName() {
        String validFullName = "John Doe";
        assertTrue(dataValidator.validateFullName(validFullName));
    }

    @Test
    public void testValidateFullName_shouldReturnFalse_whenInvalidFullName_BecouseFullNameTooShot() {
        String invalidFullName = "J";
        assertFalse(dataValidator.validateFullName(invalidFullName));
    }

    @Test
    public void testValidateFullName_shouldReturnFalse_whenInvalidFullName_BecouseFullNameTooLong() {
        String invalidFullName = "J".repeat(51);
        assertFalse(dataValidator.validateFullName(invalidFullName));
    }

    @Test
    public void testValidateFullName_shouldReturnFalse_whenInvalidFullName_BecouseFullNameHasWrongSymbols() {
        String[] invalidFullName = { "john!doe@example.com",
                                     "john#doe@example.com",
                                     "john$doe@example.com",
                                     "john%doe@example.com",
                                     "john^doe@example.com",
                                     "john&doe@example.com",
                                     "john(doe@example.com",
                                     "john)doe@example.com",
                                     "john{doe@example.com",
                                     "john}doe@example.com",
                                     "john[doe@example.com",
                                     "john]doe@example.com",
                                     "john+doe@example.com",
                                     "john\\doe@example.com",
                                     "john|doe@example.com",
                                     "johndoe@example.com",
                                     "john;doe@example.com",
                                     "john/doe@example.com",
                                     "john,doe@example.com",
                                     "john:doe@example.com",
                                     "john<doe@example.com",
                                     "john>doe@example.com",
                                     "john?doe@example.com",
                                     "john`doe@example.com" };
        for (String email : invalidFullName)
            assertFalse(dataValidator.validateFullName(email));

    }

    @Test
    public void testValidateEmail_shouldReturnTrue_whenValidEmail() {
        String validEmail = "john.doe@example.com";
        assertTrue(dataValidator.validateEmail(validEmail));
    }

    @Test
    public void testValidateEmail_shouldReturnTrue_whenEmailValidAndHasMaximumNumberOfSymbolsAfterAtSign() {
        String validEmail = "j@example.com"
                            + "m".repeat(87);
        assertTrue(dataValidator.validateEmail(validEmail));
    }

    @Test
    public void testValidateEmail_shouldReturnTrue_whenEmailValidAndHasMaximumNumberOfSymbolsBeforeAtSign() {
        String validEmail = "j".repeat(94)
                            + "@a.com";
        assertTrue(dataValidator.validateEmail(validEmail));
    }

    @Test
    public void testValidateEmail_shouldReturnFalse_whenEmailInvalidBecauseHasTooManyOfNumberOfSymbolsAfterAtSign() {
        String validEmail = "j@example.com"
                            + "m".repeat(88);
        assertFalse(dataValidator.validateEmail(validEmail));
    }

    @Test
    public void testValidateEmail_shouldReturnFalse_whenEmailInvalidBecauseHasTooManyOfNumberOfSymbolsBeforeAtSign() {
        String validEmail = "j".repeat(97)
                            + "@a.c";
        assertFalse(dataValidator.validateEmail(validEmail));
    }

    @Test
    public void testValidateEmail_shouldReturnFalse_whenEmailWithoutDomen() {
        String invalidEmail = "john.doe@";
        assertFalse(dataValidator.validateEmail(invalidEmail));
    }

    @Test
    public void testValidateEmail_shouldReturnFalse_whenEmailWithoutLocalPart() {
        String invalidEmail = "@example.com";
        assertFalse(dataValidator.validateEmail(invalidEmail));
    }

    @Test
    public void testValidateEmail_shouldReturnFalse_whenEmailIsNull() {
        String invalidEmail = null;
        assertFalse(dataValidator.validateEmail(invalidEmail));
    }

    @Test
    public void testValidateEmailIsUnique_shouldReturnTrue_whenSearchedEmailNotFound() {
        String email = "unique.email@example.com";
        when(customerRepository.findByEmail(email)).thenReturn(Optional.empty());
        assertTrue(dataValidator.validateEmailIsUnique(email));
    }

    @Test
    public void testValidateEmailIsUnique_shouldReturnFalse_whenSearchedEmailAlreadyExists() {
        String email = "existing.email@example.com";
        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(new CustomerEntity()));
        assertFalse(dataValidator.validateEmailIsUnique(email));
    }

    @Test
    public void testValidatePhone_shouldReturnTrue_whenPhoneIsNull() {
        String invalidPhone = null;
        assertTrue(dataValidator.validatePhone(invalidPhone));
    }

    @Test
    public void testValidatePhone_shouldReturnTrue_whenValidPhone() {
        String validPhone = "+380987654321";
        assertTrue(dataValidator.validatePhone(validPhone));
    }

    @Test
    public void testValidatePhone_shouldReturnTrue_whenValidPhoneWithTheMinimumNumberOfDigits() {
        String validPhone = "+387654";
        assertTrue(dataValidator.validatePhone(validPhone));
    }

    @Test
    public void testValidatePhone_shouldReturnTrue_whenValidPhoneWithTheMaximumNumberOfDigits() {
        String validPhone = "+38765432123456";
        assertTrue(dataValidator.validatePhone(validPhone));
    }

    @Test
    public void testValidatePhone_shouldReturnFalse_whenInvalidPhone_BecousePhoneTooShot() {
        String invalidPhone = "+380";
        assertFalse(dataValidator.validatePhone(invalidPhone));
    }

    @Test
    public void testValidatePhone_shouldReturnFalse_whenInvalidPhone_BecousePhoneTooLong() {
        String invalidPhone = "+380987654321".concat("234567890");
        assertFalse(dataValidator.validatePhone(invalidPhone));
    }

    @Test
    public void testValidatePhone_shouldReturnFalse_whenInvalidPhone_BecousePhoneDoesNotStartedFromPlus() {
        String invalidPhone = "380987654321";
        assertFalse(dataValidator.validatePhone(invalidPhone));
    }

    @Test
    public void testValidatePhone_shouldReturnFalse_whenInvalidPhone_BecousePhoneContainsNotOnlyDigits() {
        String invalidPhone = "+380987654cba";
        assertFalse(dataValidator.validatePhone(invalidPhone));
    }

    @Test
    public void testIsCustomerDataValid_shouldReturnTrue_whenAllDataIsValid() {
        String fullName = "John Doe";
        String email = "john.doe@example.com";
        String phone = "+380987654321";

        when(customerRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertTrue(dataValidator.isCustomerDataValid(fullName,
                                                     email,
                                                     phone));
    }

    @Test
    public void testIsCustomerDataValid_shouldReturnFalse_whenFullNameIsInvalid() {
        String fullName = "J";
        String email = "john.doe@example.com";
        String phone = "+380987654321";

        assertFalse(dataValidator.isCustomerDataValid(fullName,
                                                      email,
                                                      phone));
    }

    @Test
    public void testIsCustomerDataValid_shouldReturnFalse_whenEmailIsInvalid() {
        String fullName = "John Doe";
        String email = "john.doe";
        String phone = "+380987654321";

        assertFalse(dataValidator.isCustomerDataValid(fullName,
                                                      email,
                                                      phone));
    }

    @Test
    public void testIsCustomerDataValid_shouldReturnFalse_whenEmailIsNotUnique() {
        String fullName = "John Doe";
        String email = "john.doe@example.com";
        String phone = "+380987654321";

        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(new CustomerEntity()));

        assertFalse(dataValidator.isCustomerDataValid(fullName,
                                                      email,
                                                      phone));
    }

    @Test
    public void testIsCustomerDataValid_shouldReturnFalse_whenPhoneIsInvalid() {
        String fullName = "John Doe";
        String email = "john.doe@example.com";
        String phone = "+38";

        assertFalse(dataValidator.isCustomerDataValid(fullName,
                                                      email,
                                                      phone));
    }
}

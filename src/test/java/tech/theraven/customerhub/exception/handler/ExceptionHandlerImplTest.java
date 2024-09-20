package tech.theraven.customerhub.exception.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class ExceptionHandlerImplTest {

    @Test
    public void testHandleValidationExceptions_shouldReturnBadRequest() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getAllErrors()).thenReturn(Arrays.asList(new FieldError("customer",
                                                                                   "email",
                                                                                   "Invalid email format"),
                                                                    new FieldError("customer",
                                                                                   "phone",
                                                                                   "Phone number is required")));

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null,
                                                                                        bindingResult);

        ExceptionHandlerImpl exceptionHandler = new ExceptionHandlerImpl();
        ResponseEntity<String> response = exceptionHandler.handleValidationExceptions(exception);

        assertEquals(HttpStatus.BAD_REQUEST,
                     response.getStatusCode());
        assertEquals("Invalid email format; Phone number is required; ",
                     response.getBody());
    }
}

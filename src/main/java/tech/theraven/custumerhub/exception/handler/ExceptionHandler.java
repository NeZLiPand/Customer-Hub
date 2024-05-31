package tech.theraven.custumerhub.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

public interface ExceptionHandler {

    ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException exception);

}

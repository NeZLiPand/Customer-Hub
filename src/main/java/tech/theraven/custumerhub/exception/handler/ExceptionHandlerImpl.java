package tech.theraven.custumerhub.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class ExceptionHandlerImpl
    implements
        ExceptionHandler {

    @Override
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException exception) {
        String errorMessage = exception.getBindingResult()
                                       .getAllErrors()
                                       .stream()
                                       .map(error -> error.getDefaultMessage())
                                       .reduce("",
                                               (messages,
                                                message) -> messages
                                                            + message
                                                            + "; ");
        return new ResponseEntity<>(errorMessage,
                                    HttpStatus.BAD_REQUEST);
    }

}

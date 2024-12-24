package gr.aueb.cf.myproject.core;

import gr.aueb.cf.myproject.core.exceptions.*;
import gr.aueb.cf.myproject.dto.ResponseMessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ValidationException ex) {

        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {

        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AppObjectNotFoundException.class})
    public ResponseEntity<ResponseMessageDTO> handleConstraintViolationException(AppObjectNotFoundException ex) {
        return new ResponseEntity<>(new ResponseMessageDTO(ex.getMessage(), ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AppObjectAlreadyExists.class})
    public ResponseEntity<ResponseMessageDTO> handleConstraintViolationException(AppObjectAlreadyExists ex) {
        return new ResponseEntity<>(new ResponseMessageDTO(ex.getMessage(), ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({AppObjectInvalidArgumentException.class})
    public ResponseEntity<ResponseMessageDTO> handleConstraintViolationException(AppObjectInvalidArgumentException ex) {
        return new ResponseEntity<>(new ResponseMessageDTO(ex.getMessage(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AppObjectNotAuthorizedException.class})
    public ResponseEntity<ResponseMessageDTO> handleConstraintViolationException(AppObjectNotAuthorizedException ex) {
        return new ResponseEntity<>(new ResponseMessageDTO(ex.getMessage(), ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({AppServerException.class})
    public ResponseEntity<ResponseMessageDTO> handleConstraintViolationException(AppServerException ex) {
        return new ResponseEntity<>(new ResponseMessageDTO(ex.getMessage(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
package org.vcpl.triton.validation;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import springfox.documentation.service.ResponseMessage;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        //return new ResponseEntity(errors, BAD_REQUEST);
        return new ResponseEntity<>(getBody(BAD_REQUEST, ex, "Please enter a valid value for these Parameters " + errors.keySet(),"SCF002"), new HttpHeaders(), BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errorMessages = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        return new ResponseEntity<>(getBody(BAD_REQUEST, ex, "Already Existing Customer" + errorMessages,"SCF002"), new HttpHeaders(), BAD_REQUEST);

    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Object> handleNumberFormatException(NumberFormatException ex) {
        return new ResponseEntity<>(getBody(BAD_REQUEST, ex, "Please enter a valid value", "SCF002"), new HttpHeaders(), BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(getBody(BAD_REQUEST, ex, ex.getMessage(), "SCF002"), new HttpHeaders(), BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<>(getBody(FORBIDDEN, ex, "Access Denied", "SCF005"), new HttpHeaders(), FORBIDDEN);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementExceptionException(NoSuchElementException ex) {
        logger.error(" | URL | NoSuchElementException | OPERATION | "+" Error |"+ ex.getMessage());
        return new ResponseEntity<>(getBody(BAD_REQUEST, ex, "No value present", "SCF005"), new HttpHeaders(), BAD_REQUEST);
    }

//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
//        return new ResponseEntity<>(getBody(UNAUTHORIZED, ex, "Bad Credentials", "SCF004"), new HttpHeaders(), UNAUTHORIZED);
//    }

    public Map<String, Object> getBody(HttpStatus status, Exception ex, String message, String returnCode) {


        Map<String, Object> body = new LinkedHashMap<>();
        body.put("returnCode", returnCode);
        body.put("message", message);
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        //body.put("error-message", ex.toString());
/*
        Throwable cause = ex.getCause();
        if (cause != null) {
            body.put("exceptionCause", ex.getCause().toString());
        }*/
        return body;
    }

}


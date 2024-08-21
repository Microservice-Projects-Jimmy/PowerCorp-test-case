package net.jemsit.Auth.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class MyControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> userNotFoundError(UserNotFoundException userNotFoundException) {
        return new ResponseEntity<>("User not found!", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> usernameIsNotAvailable(UserAlreadyExistsException userAlreadyExistsException) {
        return new ResponseEntity<>("Username is not available!", HttpStatus.BAD_REQUEST);
    }

    //    ALWAYS KEEP THIS METHOD AT THE BOTTOM
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, List<String>> body = new HashMap<>();

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}

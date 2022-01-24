package com.leverx.exceptionhandler;

import com.leverx.exceptions.EmailException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class DefaultExceptionHandler {

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ErrorMessage> onResponseStatusException(ResponseStatusException e) {
    return new ResponseEntity<>(new ErrorMessage(e.getClass().getSimpleName(), e.getMessage()), BAD_REQUEST);
  }

  @ExceptionHandler(SQLException.class)
  public ResponseEntity<ErrorMessage> onSQLException(SQLException e) {
    return new ResponseEntity<>(new ErrorMessage(e.getClass().getSimpleName(), e.getMessage()), BAD_REQUEST);
  }

  @ExceptionHandler(IOException.class)
  public ResponseEntity<ErrorMessage> onIOException(IOException e) {
    return new ResponseEntity<>(new ErrorMessage(e.getClass().getSimpleName(), e.getMessage()), NOT_FOUND);
  }

  @ExceptionHandler(ParseException.class)
  public ResponseEntity<ErrorMessage> onParseException(ParseException e) {
    return new ResponseEntity<>(new ErrorMessage(e.getClass().getSimpleName(), e.getMessage()), BAD_REQUEST);
  }

  @ExceptionHandler(EmailException.class)
  public ResponseEntity<ErrorMessage> onEmailException(EmailException e) {
    return new ResponseEntity<>(new ErrorMessage(e.getClass().getSimpleName(), e.getMessage()), BAD_REQUEST);
  }
}

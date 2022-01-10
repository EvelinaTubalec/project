package com.leverx.exceptionhandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.sql.SQLException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class DefaultExceptionHandler {

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ErrorMessage> onResponseStatusException(ResponseStatusException e) {
    return new ResponseEntity<>(new ErrorMessage(e.getMessage()), BAD_REQUEST);
  }

  @ExceptionHandler(SQLException.class)
  public ResponseEntity<ErrorMessage> onSQLException(SQLException e) {
    return new ResponseEntity<>(new ErrorMessage(e.getMessage()), BAD_REQUEST);
  }
}

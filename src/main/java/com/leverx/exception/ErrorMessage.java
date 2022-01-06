package com.leverx.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ErrorMessage {

  private String fieldName;

  private String message;

  public ErrorMessage(String message) {
    this.message = message;
  }
}

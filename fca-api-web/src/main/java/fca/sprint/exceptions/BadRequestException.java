package com.capitalone.uk.template.exceptions;


public class BadRequestException extends RuntimeException {

  public BadRequestException(String message) {
    super(message);
  }
}

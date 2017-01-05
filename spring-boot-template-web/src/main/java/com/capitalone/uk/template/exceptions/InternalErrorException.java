package com.capitalone.uk.template.exceptions;

public class InternalErrorException extends RuntimeException {
  public InternalErrorException(String message) {
    super("Internal system error. Message: " + message);
  }
}

package com.capitalone.uk.template.exceptions.handlers;


import com.capitalone.uk.template.exceptions.BadRequestException;
import com.capitalone.uk.template.exceptions.InternalErrorException;
import com.capitalone.uk.template.exceptions.NotFoundException;
import com.capitalone.uk.template.models.ErrorResponse;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class GlobalExceptionHandlerTest {

  private GlobalExceptionHandler globalExceptionHandler;

  @Before
  public void setUp() {
    globalExceptionHandler = new GlobalExceptionHandler();
  }

  @Test
  public void handleBadRequestExceptionReturnsCorrectStatusCodeAndObject() throws Exception {
    BadRequestException ex = new BadRequestException("Error");
    ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleBadRequestException(ex);
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(responseEntity.getBody()).isInstanceOf(ErrorResponse.class);
  }

  @Test
  public void handleNotFoundExceptionReturnsCorrectStatusCodeAndObject() throws Exception {
    NotFoundException ex = new NotFoundException("Error");
    ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleNotFoundException(ex);
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(responseEntity.getBody()).isInstanceOf(ErrorResponse.class);
  }

  @Test
  public void handleInternalErrorExceptionReturnsCorrectStatusCodeAndObject() throws Exception {
    InternalErrorException ex = new InternalErrorException("Error");
    ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleInternalErrorException(ex);
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    assertThat(responseEntity.getBody()).isInstanceOf(ErrorResponse.class);
  }
}

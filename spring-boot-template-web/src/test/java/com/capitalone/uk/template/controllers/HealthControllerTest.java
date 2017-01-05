package com.capitalone.uk.template.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class HealthControllerTest {

  private HealthController healthController;

  @Before
  public void setUp() {
    healthController = new HealthController();
  }

  @Test
  public void testHealthCheckOK() {
    ResponseEntity<?> response = healthController.healthCheck();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

}
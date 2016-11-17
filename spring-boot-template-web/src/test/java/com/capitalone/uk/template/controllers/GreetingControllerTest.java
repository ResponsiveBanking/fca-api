package com.capitalone.uk.template.controllers;


import com.capitalone.uk.template.models.Greeting;
import com.capitalone.uk.template.services.GreetingService;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;

public class GreetingControllerTest {

  private GreetingService greetingServiceMock;
  private GreetingController greetingController;

  @Before
  public void setUp() {
    greetingServiceMock = mock(GreetingService.class);
    greetingController = new GreetingController(greetingServiceMock);
  }

  @Test
  public void greetingReturnsHelloWorldTest() {
    given(greetingServiceMock.getGreeting(anyString())).willReturn("Hello, World!");
    Greeting body = greetingController.greeting(anyString());
    assertThat(body.getContent()).isEqualTo("Hello, World!");
  }
}

package com.capitalone.uk.template.steps;

import org.apache.http.HttpStatus;
import org.junit.Ignore;

import java.util.HashMap;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Ignore
public class HealthStepDefinition extends StepDefinitionBase {

  private static final String HEALTH_CHECK_ENDPOINT = "/api/health";

  @When("^a health check is made$")
  public void aHealthCheckIsMade() throws Throwable {
    makeGetCall(HEALTH_CHECK_ENDPOINT, new HashMap<>());
  }

  @Then("^the health check confirms the service is responsive$")
  public void theHealthCheckConfirmsTheServiceIsResponsive() throws Throwable {
    response.statusCode(equalTo(HttpStatus.SC_OK));
  }
}

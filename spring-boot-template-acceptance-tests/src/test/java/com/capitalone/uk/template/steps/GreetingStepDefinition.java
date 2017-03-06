package com.capitalone.uk.template.steps;


import org.apache.http.HttpStatus;
import org.junit.Ignore;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static com.jayway.restassured.path.json.JsonPath.from;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@Ignore
public class GreetingStepDefinition extends StepDefinitionBase {

  @Given("^the service is running$")
  public void theServiceIsRunning() throws Throwable {

  }

  @When("^the greeting request is made$")
  public void theGreetingRequestIsMade() throws Throwable {
    makeGetCall("/api/greeting", defaultHeaders);
  }

  @Then("^a successful response is returned$")
  public void aSuccessfulResponseIsReturned() throws Throwable {
    response.statusCode(equalTo(HttpStatus.SC_OK));
    response.body("content", equalTo("Hello, World!"));
  }
}

package com.capitalone.uk.template.steps;


import com.capitalone.uk.template.AcceptanceTestBase;

import org.apache.http.HttpStatus;
import org.junit.Ignore;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static com.jayway.restassured.path.json.JsonPath.from;
import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class GreetingStepDefinition extends AcceptanceTestBase{

  @Given("^the service is running$")
  public void theServiceIsRunning() throws Throwable {

  }

  @When("^the greeting request is made$")
  public void theGreetingRequestIsMade() throws Throwable {
    makeGetCall("/greeting");
  }

  @Then("^a successful response is returned$")
  public void aSuccessfulResponseIsReturned() throws Throwable {
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SC_OK);

    String actualResponseMessage = from(response.jsonPath().prettify()).get("content");
    assertThat(actualResponseMessage).isEqualTo("Hello, World!");
  }
}

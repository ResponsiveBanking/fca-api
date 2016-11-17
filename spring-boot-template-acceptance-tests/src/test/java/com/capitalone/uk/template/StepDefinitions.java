package com.capitalone.uk.template;

import com.capitalone.uk.template.ApplicationLauncher;
import com.jayway.restassured.response.Response;

import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.path.json.JsonPath.from;

@Ignore
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ApplicationLauncher.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class StepDefinitions {

  private Response response;

  @Given("^the service is running$")
  public void theServiceIsRunning() throws Throwable {

  }

  @When("^the greeting request is made$")
  public void theGreetingRequestIsMade() throws Throwable {
    response = given().log().all().urlEncodingEnabled(true).
        when().
        get("http://localhost:8080/greeting");
  }

  @Then("^a successful response is returned$")
  public void aSuccessfulResponseIsReturned() throws Throwable {
    Assert.assertEquals(HttpStatus.SC_OK, response.getStatusCode());

    String actualResponseMessage = from(response.jsonPath().prettify()).get("content");
    Assert.assertEquals("Hello, World!", actualResponseMessage);
  }
}

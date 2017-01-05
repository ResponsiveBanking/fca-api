package com.capitalone.uk.template;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.given;

@Ignore
@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTestBase {

  @LocalServerPort
  private int port;

  protected Response response;


  public Response makeGetCall(String url) {
    response = given().urlEncodingEnabled(false).log().all()
        .when()
        .port(port)
        .get(url);
    return response;
  }

  public Response makePostCallWithJson(String url, String jsonBody) {
    response = given().urlEncodingEnabled(false).log().all()
        .contentType(ContentType.JSON)
        .body(jsonBody)
        .when()
        .port(port)
        .post(url);
    return response;
  }

  public Response makePutCallWithJson(String url, String jsonBody) {
    response = given().urlEncodingEnabled(false).log().all()
        .contentType(ContentType.JSON)
        .body(jsonBody)
        .when()
        .port(port)
        .put(url);
    return response;
  }
}

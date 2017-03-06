package com.capitalone.uk.template.steps;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;

@Ignore
@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StepDefinitionBase {

  @LocalServerPort
  private int port;

  protected ValidatableResponse response;

  protected static Map<String, String> defaultHeaders;

  static {
    defaultHeaders = new HashMap<>();
    defaultHeaders.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    defaultHeaders.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
  }

  protected void makeGetCall(String url, Map<String, String> headers) {
    headers = ObjectUtils.defaultIfNull(headers, new HashMap<>());
    response = given().urlEncodingEnabled(false).log().all()
        .headers(headers)
        .when()
        .port(port)
        .get(url)
        .then();
  }

  protected void makePostCallWithJson(String url, Map<String, String> headers, Object body) {
    headers = ObjectUtils.defaultIfNull(headers, new HashMap<>());
    response = given().urlEncodingEnabled(false).log().all()
        .headers(headers)
        .body(body)
        .when()
        .port(port)
        .post(url)
        .then();
  }

  protected void makePutCallWithJson(String url, Map<String, String> headers, Object body) {
    headers = ObjectUtils.defaultIfNull(headers, new HashMap<>());
    response = given().urlEncodingEnabled(false).log().all()
        .headers(headers)
        .body(body)
        .when()
        .port(port)
        .put(url)
        .then();
  }

  protected void makeDeleteCall(String strUrl, Map<String, String> headers, Object body) {
    headers = ObjectUtils.defaultIfNull(headers, new HashMap<>());
    response = given().urlEncodingEnabled(true).log().all()
        .headers(headers)
        .body(body)
        .when()
        .port(port)
        .delete(strUrl)
        .then();
  }
}

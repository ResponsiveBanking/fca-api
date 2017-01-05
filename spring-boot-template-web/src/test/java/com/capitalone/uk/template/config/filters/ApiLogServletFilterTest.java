package com.capitalone.uk.template.config.filters;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import uk.org.lidalia.slf4jext.Level;
import uk.org.lidalia.slf4jtest.LoggingEvent;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests using Lidalia for the Api Log Filter.
 */
public class ApiLogServletFilterTest {

  private final TestLogger LOG = TestLoggerFactory.getTestLogger(ApiLogServletFilter.class);
  private final String IP_ADDRESS = "172.0.0.1";
  private final String METHOD_NAME = "GET";
  private final String REQUEST_URI = "testUri";
  private final int STATUS_CODE = HttpStatus.OK.value();

  private final HttpServletRequest MOCK_REQUEST = mock(HttpServletRequest.class);
  private final HttpServletResponse MOCK_RESPONSE = mock(HttpServletResponse.class);
  private final FilterChain MOCK_FILTER_CHAIN = mock(FilterChain.class);

  private ApiLogServletFilter filterUnderTest;

  /**
   * Sets up the lidia LOG and a mock MOCK_REQUEST and MOCK_RESPONSE.
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    filterUnderTest =  new ApiLogServletFilter();
    when(MOCK_REQUEST.getMethod()).thenReturn(METHOD_NAME);
    when(MOCK_REQUEST.getRemoteAddr()).thenReturn(IP_ADDRESS);
    when(MOCK_RESPONSE.getStatus()).thenReturn(STATUS_CODE);
    when(MOCK_REQUEST.getRequestURI()).thenReturn(REQUEST_URI);
  }

  /**
   * Test that the expected IP address is logged.
   */
  @Test
  public void ipAddressIsLoggedInDoFilter() throws Exception {
    assertDoFilterLogContains("IpAddress: " + IP_ADDRESS);
  }

  /**
   * Runs the filterUnderTest and asserts the output contains the search string.
   * @param searchString the string which should be contained in the log.
   * @throws Exception
   */
  private void assertDoFilterLogContains(String searchString) throws Exception {
    filterUnderTest.doFilter(MOCK_REQUEST, MOCK_RESPONSE, MOCK_FILTER_CHAIN);
    String actualLog = LOG.getLoggingEvents().get(0).getMessage();
    assertThat(actualLog).contains(searchString);
  }

  /**
   * Test the HTTP method returned is the same as the MOCK_RESPONSE returned from the requests getMethod.
   */
  @Test
  public void httpMethodIsLoggedInDoFilter() throws Exception {
    assertDoFilterLogContains("HttpMethod: " + METHOD_NAME);
  }

  /**
   * Test that the http status that is logged is the same as the status on the MOCK_RESPONSE.
   */
  @Test
  public void httpStatusIsLoggedInDoFilter() throws Exception{
    assertDoFilterLogContains("HttpStatus: " + STATUS_CODE);
  }

  /**
   * Test that the description is the correct description for the status.
   */
  @Test
  public void httpDescriptionIsLoggedInDoFilter() throws Exception {
    assertDoFilterLogContains("StatusDescription: OK");
  }

  /**
   * Test that the realtive URI is the same one given in the MOCK_REQUEST.
   */
  @Test
  public void httpRelativeUrlIsLoggedInDoFilter() throws Exception {
    assertDoFilterLogContains("RelativeURI: " + REQUEST_URI);
  }

  /**
   * Test that when an exception is thrown, an error is logged.
   * @throws Exception an exception from the doFilter method, will cause test failure.
   */
  @Test
  public void exceptionThrownInsideFilterIsCaught() throws Exception {
    when(MOCK_REQUEST.getMethod()).thenThrow(new IllegalArgumentException("Test exception"));
    TestLoggerFactory.clear();
    filterUnderTest.doFilter(MOCK_REQUEST, MOCK_RESPONSE, MOCK_FILTER_CHAIN);

    LoggingEvent errorLoggingEvent = LOG.getLoggingEvents().get(0);
    assertThat(LOG.getLoggingEvents().size()).isEqualTo(1);
    assertThat(errorLoggingEvent.getMessage()).contains("ApiLogServletFilter Exception");
    assertThat(errorLoggingEvent.getLevel()).isEqualTo(Level.ERROR);
  }

  /**
   * Test init won't cause any exceptions.
   * @throws Exception
   */
  @Test
  public void initDoesNotThrowExceptions() throws Exception {
    filterUnderTest.init(null);
  }

  /**
   * Test destroy doesn't cause any exceptions.
   * @throws Exception
   */
  @Test
  public void destroyDoesNotThrowExceptions() throws Exception {
    filterUnderTest.destroy();
  }

  /**
   * Clear down the log ready for the next test.
   * @throws Exception the log failed to clear.
   */
  @After
  public void tearDown() throws Exception {
    TestLoggerFactory.clear();
  }
}
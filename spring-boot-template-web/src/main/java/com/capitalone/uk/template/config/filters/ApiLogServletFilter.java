package com.capitalone.uk.template.config.filters;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter
public class ApiLogServletFilter implements Filter {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  public void init(FilterConfig filterConfig) throws ServletException {
  }

  public void destroy() {
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    // Start recording start time of the request
    long requestTime = System.currentTimeMillis();

    try {
      filterChain.doFilter(httpRequest, response);
    } finally {
      long responseTime = System.currentTimeMillis() - requestTime;

      StringBuilder stringBuilder = new StringBuilder();
      try {
        stringBuilder.append("IpAddress: ");
        stringBuilder.append(httpRequest.getRemoteAddr());

        stringBuilder.append(" | HttpMethod: ");
        stringBuilder.append(httpRequest.getMethod());

        stringBuilder.append(" | ResponseTime: ");
        stringBuilder.append(responseTime);
        stringBuilder.append(" ms");

        int httpStatusCode = httpResponse.getStatus();
        stringBuilder.append(" | HttpStatus: ");
        stringBuilder.append(httpStatusCode);

        stringBuilder.append(" | StatusDescription: ");
        stringBuilder.append(resolveStatusDescription(httpStatusCode));

        stringBuilder.append(" | RelativeURI: ");
        stringBuilder.append(httpRequest.getRequestURI());

        logger.info(stringBuilder.toString());
      } catch (Exception e) {
        logger.error("Performance Handler Exception", e);
      }
    }
  }

  /**
   * Resolves the status description.
   */
  private String resolveStatusDescription(int httpStatusCode) {
    String value = null;
    if (httpStatusCode >= 200 && httpStatusCode < 300) {
      value = "SUCCESS";
    } else if (httpStatusCode >= 500) {
      value = "ERR_GENERAL_ERROR";
    } else {
      value = "CLIENT_ERROR";
    }
    return value;
  }
}

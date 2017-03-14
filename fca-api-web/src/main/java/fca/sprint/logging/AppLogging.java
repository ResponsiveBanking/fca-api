package com.capitalone.uk.template.logging;

import org.apache.commons.lang3.tuple.Pair;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class AppLogging {

  private static final String AFTER_RETURNING = "exiting method {}() ";
  private static final String THROWING = "threw the exception ";
  private static final String SYSTEM_LOG_FILE_LOGGER_NAME = "SysLog";

  /**
   * Pointcut that picks out the execution of any method.
   */
  @Pointcut("execution(* *.*(..))")
  protected void allMethod() {
  }

  /**
   * This pointcut captures any bean with a class level logging attribute set.
   */
  @Pointcut("within(@com.capitalone.uk.template.logging.annotations.Log *)")
  public void logBean() {
  }

  @AfterThrowing(pointcut = "allMethod() && logBean()", throwing = "exception")
  public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
    Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
    Logger systemLogger = LoggerFactory.getLogger(SYSTEM_LOG_FILE_LOGGER_NAME);

    Pair<String, Object[]> pair = buildParams(joinPoint.getSignature().getName(), exception);
    logger.error(pair.getKey(), pair.getValue());
    if (exception.getCause() != null) {
      logger.error("Cause: " + exception.getCause());
    }
    systemLogger.error("Stack trace: ", exception);
  }

  private Pair<String, Object[]> buildParams(String signature, Object... params) {
    final Object[] loggingParams = new Object[params.length + 1];
    loggingParams[0] = signature;
    final StringBuilder buffer = new StringBuilder(AFTER_RETURNING);

    if(params.length > 0) {
      buffer.append(THROWING);
      System.arraycopy(params, 0, loggingParams, 1, params.length);
      for (int i = 0; i < params.length; i++) {
        buffer.append(" {} ");
      }
    }

    return Pair.of(buffer.toString(), loggingParams);
  }
}

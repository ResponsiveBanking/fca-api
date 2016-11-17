package com.capitalone.uk.template.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProfileLogging {

  public static final String PROFILE_STRING = "{}.{} execution time: {} ms";

  protected final Logger logger = LoggerFactory.getLogger("PerfLog");

  /**
   * Pointcut that picks out the execution of any public method.
   */
  @Pointcut(value = "execution(public * *(..)) ")
  public void anyPublicMethod() {
  }

  /**
   * This pointcut captures any bean with a class level profile attribute set.
   */
  @Pointcut("within(@com.capitalone.uk.template.logging.annotations.Profile *)")
  public void profileBean() {
  }

  /**
   * This pointcut captures any bean with a method level profile attribute set.
   */
  @Pointcut("anyPublicMethod() && @annotation(com.capitalone.uk.template.logging.annotations.Profile)")
  public void profileMethod() {
  }

  /**
   * Method which intercepts the call and applies profiling for a given method, namely it will track the amount of time that a given method takes to execute.
   *
   * @param proceedingJoinPoint
   * @return
   * @throws Throwable
   */
  @Around("profileBean() || profileMethod()")
  public Object doPublicProfiling(ProceedingJoinPoint proceedingJoinPoint) throws Throwable { //NOSONAR
    Long start = System.currentTimeMillis();

    try {
      return proceedingJoinPoint.proceed();
    }
    finally {
      Long end = System.currentTimeMillis() - start;

      Object[] parms = {proceedingJoinPoint.getTarget().getClass().toString(),
          proceedingJoinPoint.getSignature().getName(), end};

      logger.info(PROFILE_STRING, parms);
    }
  }
}

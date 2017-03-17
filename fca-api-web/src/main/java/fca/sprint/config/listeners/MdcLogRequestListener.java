package fca.sprint.config.listeners;


import org.slf4j.MDC;

import java.util.UUID;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MdcLogRequestListener implements ServletRequestListener {

  @Override
  public void requestInitialized(ServletRequestEvent servletRequestEvent) {
    String requestId = servletRequestEvent.getServletRequest().getRemoteAddr() + "-" + UUID.randomUUID();
    MDC.put("requestId", requestId);
  }

  @Override
  public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
    MDC.clear();
  }
}

package fca.sprint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class ApplicationLauncher {

  public static void main(String[] args) {
    SpringApplication.run(ApplicationLauncher.class, args);
  }
}

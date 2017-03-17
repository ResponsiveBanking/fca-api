package fca.sprint.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder.build();
  }

  @Bean
  public HttpEntity httpEntity() {
    HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.add("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjaSI6Im9hdXRoY2xpZW50XzAwMDA5NFB2SU5ER3pUM2s2dHo4anAiLCJleHAiOjE0ODk1MTQ2NDQsImlhdCI6MTQ4OTQ5MzA0NCwianRpIjoidG9rXzAwMDA5SU02RnNMTGxKc2xmUzJoOEwiLCJ1aSI6InVzZXJfMDAwMDk4WjR3R2Jmbnp0djFMNzdrOSIsInYiOiIyIn0.IfSPA1W0zsUxSNDIIMpkKu0edqxwF5jPNLVaSTxdBOE");
    return new HttpEntity<>(requestHeaders);
  }

  @Bean
  public Docket setupSwagger() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("template")
        .apiInfo(apiInfo())
        .useDefaultResponseMessages(false)
        .select()
        .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Template")
        .description("Description")
        .version("1.0.0")
        .build();
  }
}

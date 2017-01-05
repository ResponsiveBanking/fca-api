package com.capitalone.uk.template.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        .version("0.1")
        .build();
  }
}

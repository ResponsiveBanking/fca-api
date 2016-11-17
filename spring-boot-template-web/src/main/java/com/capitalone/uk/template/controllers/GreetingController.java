package com.capitalone.uk.template.controllers;

import com.capitalone.uk.template.logging.annotations.Log;
import com.capitalone.uk.template.logging.annotations.Profile;
import com.capitalone.uk.template.models.Greeting;
import com.capitalone.uk.template.services.GreetingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log
@Profile
@RestController
public class GreetingController {

  private final GreetingService greetingService;

  @Autowired
  public GreetingController(GreetingService greetingService) {
    this.greetingService = greetingService;
  }

  @RequestMapping("/greeting")
  public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
    return new Greeting(1, greetingService.getGreeting(name));
  }
}
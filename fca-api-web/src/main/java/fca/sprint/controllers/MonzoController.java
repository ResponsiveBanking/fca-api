package fca.sprint.controllers;

import fca.sprint.logging.annotations.Log;
import fca.sprint.logging.annotations.Profile;
import fca.sprint.models.Greeting;
import fca.sprint.services.GreetingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Log
@Profile
@RestController
public class GreetingController {

  private RestTemplate restTemplate;
  private HttpEntity httpEntity;

  private String accountId = "acc_000098iHM9GGgWiktQ2Ql7";

  @Autowired
  public GreetingController(RestTemplate restTemplate,
                            HttpEntity httpEntity) {
    this.restTemplate = restTemplate;
    this.httpEntity = httpEntity;
  }

  @RequestMapping("/customers")
  public ResponseEntity<?> getCustomers() {
    return restTemplate.exchange("https://api.monzo.com/transactions?account_id=" + accountId, HttpMethod.GET, httpEntity, Object.class);
  }
}
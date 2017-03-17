package fca.sprint.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import fca.sprint.logging.annotations.Log;
import fca.sprint.logging.annotations.Profile;
import fca.sprint.models.FirebaseResponse;
import fca.sprint.repositories.TransactionRepository;
import fca.sprint.services.AndroidPushNotificationsService;

@Log
@Profile
@RestController
public class NotificationController {

  private static final Logger log = LoggerFactory.getLogger(NotificationController.class);

  private AndroidPushNotificationsService androidPushNotificationsService;

  private ObjectMapper mapper = new ObjectMapper();

  @Autowired
  public NotificationController(AndroidPushNotificationsService androidPushNotificationsService) {
    this.androidPushNotificationsService = androidPushNotificationsService;
  }

  @RequestMapping(value = "/send", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<String> send() {


    ObjectNode body = mapper.createObjectNode();
    // JsonArray registration_ids = new JsonArray();
    // body.put("registration_ids", registration_ids);
    //body.put("to", "xxxxxxxxxxxxxxxxxxxjPwZpLgLpji_");
    //body.put("priority", "high");
    // body.put("dry_run", true);

    ObjectNode notification = mapper.createObjectNode();
    notification.put("body", "body string here");
    notification.put("title", "title string here");
    // notification.put("icon", "myicon");

    ObjectNode data = mapper.createObjectNode();
    data.put("key1", "value1");
    data.put("key2", "value2");

    body.putPOJO("notification", notification);
    body.putPOJO("data", data);

    HttpEntity<String> request = new HttpEntity<>(body.toString());

    CompletableFuture<FirebaseResponse> pushNotification = androidPushNotificationsService.send(request);
    CompletableFuture.allOf(pushNotification).join();

    try {
      FirebaseResponse firebaseResponse = pushNotification.get();
      if (firebaseResponse.getSuccess() == 1) {
        log.info("push notification sent ok!");
      } else {
        log.error("error sending push notifications: " + firebaseResponse.toString());
      }
      return new ResponseEntity<>(firebaseResponse.toString(), HttpStatus.OK);

    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
    return new ResponseEntity<>("the push notification cannot be send.", HttpStatus.BAD_REQUEST);
  }
}

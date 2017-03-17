package fca.sprint.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import fca.sprint.models.FirebaseResponse;

@Service
public class AndroidPushNotificationsService {

  private static final String FIREBASE_SERVER_KEY = "AAAAg_KmKLU:APA91bGw0bqk4ZI8RlTyDeI91EQrEAJqOx4GXpF-F_NNUvSW5t_xHbbACKI0UAiN8WoAqSHs-OYEJcY_t-Y3i3X9YR4VpDIeGJQ65_tPtAZo4u1Z1zXIesrWCFUGwzoX1wffknd86Sg5";

  @Async
  public CompletableFuture<FirebaseResponse> send(HttpEntity<String> entity) {

    RestTemplate restTemplate = new RestTemplate();

    ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
    interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
    interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
    restTemplate.setInterceptors(interceptors);

    FirebaseResponse firebaseResponse = restTemplate.postForObject("https://fcm.googleapis.com/fcm/send", entity, FirebaseResponse.class);

    return CompletableFuture.completedFuture(firebaseResponse);
  }

}
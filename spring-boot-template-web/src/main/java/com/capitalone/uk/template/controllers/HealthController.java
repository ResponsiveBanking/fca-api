package com.capitalone.uk.template.controllers;

import com.capitalone.uk.template.logging.annotations.Log;
import com.capitalone.uk.template.logging.annotations.Profile;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpURLConnection;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Log
@Profile
@RestController
@RequestMapping("/health")
@Api(value = "/health", description = "Check API health")
public class HealthController {

  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation(value = "API Health check", notes = "Check if API is running")
  @ApiResponses(value = {
      @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Api is up and running")})
  public ResponseEntity<?> healthCheck() {
    return ResponseEntity.ok().build();
  }
}

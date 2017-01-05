$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("greetings.feature");
formatter.feature({
  "line": 1,
  "name": "Successfuly received greeting",
  "description": "",
  "id": "successfuly-received-greeting",
  "keyword": "Feature"
});
formatter.scenario({
  "line": 3,
  "name": "Greeting is returned successfully from a request",
  "description": "",
  "id": "successfuly-received-greeting;greeting-is-returned-successfully-from-a-request",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 4,
  "name": "the service is running",
  "keyword": "Given "
});
formatter.step({
  "line": 5,
  "name": "the greeting request is made",
  "keyword": "When "
});
formatter.step({
  "line": 6,
  "name": "a successful response is returned",
  "keyword": "Then "
});
formatter.match({
  "location": "GreetingStepDefinition.theServiceIsRunning()"
});
formatter.result({
  "duration": 90189273,
  "status": "passed"
});
formatter.match({
  "location": "GreetingStepDefinition.theGreetingRequestIsMade()"
});
formatter.result({
  "duration": 647657002,
  "status": "passed"
});
formatter.match({
  "location": "GreetingStepDefinition.aSuccessfulResponseIsReturned()"
});
formatter.result({
  "duration": 264770922,
  "status": "passed"
});
});
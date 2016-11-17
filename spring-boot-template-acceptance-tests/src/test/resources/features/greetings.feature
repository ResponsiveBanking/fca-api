Feature: Successfuly received greeting

  Scenario: Greeting is returned successfully from a request
    Given the service is running
    When the greeting request is made
    Then a successful response is returned


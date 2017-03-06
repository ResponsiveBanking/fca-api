Feature: We can see if the service is responsive

  Scenario: Checking if the service is responsive
    When a health check is made
    Then the health check confirms the service is responsive

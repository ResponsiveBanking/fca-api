package com.capitalone.uk.template;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(format = {"html:target/CucumberResults/html/cucumber-html-report",
    "json:target/CucumberResults/json/cucumber-reports.json"},
    features = "src/test/resources/features")
public class CucumberTest {

}
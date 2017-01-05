package com.capitalone.uk.template;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:build/cucumber"},
    features = "src/test/resources/features",
    glue = "com.capitalone.uk.template")
public class CucumberTest {

}
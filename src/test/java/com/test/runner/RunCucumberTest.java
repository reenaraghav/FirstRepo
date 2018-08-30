package com.test.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;


@CucumberOptions(features = "src/test/java/com/test/features", 
glue={"com.test.stepdefinitions","com.test.util"},
plugin = { "pretty",
		"junit:target/cucumber-reports/Cucumber.xml",
        "html:target/cucumber-reports",
        "rerun:target/rerun.txt",
        "json:target/cucumber-reports/Cucumber.json" })
public class RunCucumberTest extends AbstractTestNGCucumberTests{

}

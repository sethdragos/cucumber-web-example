package com.sethdragos.bdd.runner;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        monochrome = true,
        format = {"pretty", "html:target/cucumber"},
        glue = "com.sethdragos.bdd.steps",
        features = {"classpath:cucumber/diverse_operatii.feature", "classpath:cucumber/elemente_editabile.feature"}
)
public class RunCalculatorTest {
}

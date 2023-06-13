package SOT.Squad.code.generation.Cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:SOT/Squad/code/generation/Cucumber/Users.feature", glue = "SOT.Squad.code.generation.Cucumber")
public class cruduserstest {


}

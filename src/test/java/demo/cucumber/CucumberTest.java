package demo.cucumber;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * execute a tag mvn test -Dcucumber.filter.tags="@tagx"
 * 
 */

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources"
    ,glue = {"demo.cucumber"}
)
public class CucumberTest {

}

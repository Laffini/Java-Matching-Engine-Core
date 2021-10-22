package net.laffyco.javamatchingengine.scenarios;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

import static io.cucumber.junit.CucumberOptions.SnippetType.CAMELCASE;

/**
 * Cucumber test runner.
 *
 * @author Laffini
 */
@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "summary"}, snippets = CAMELCASE,
        features = "src/test/java/net/laffyco/javamatchingengine/scenarios/")
public class CucumberRunner {
    // Intentionally left blank.
}

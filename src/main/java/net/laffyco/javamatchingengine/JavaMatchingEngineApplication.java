package net.laffyco.javamatchingengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Service bootstrap.
 *
 * @author Laffini
 *
 */
@SpringBootApplication
public class JavaMatchingEngineApplication {

    /**
     * Start the service.
     *
     * @param args
     */
    public static void main(final String[] args) {
        new JavaMatchingEngineApplication().run(args);
    }

    /**
     * Constructor.
     */
    public JavaMatchingEngineApplication() {

    }

    /**
     * Run the application.
     *
     * @param args
     */
    public void run(final String[] args) {
        SpringApplication.run(JavaMatchingEngineApplication.class, args);
    }
}

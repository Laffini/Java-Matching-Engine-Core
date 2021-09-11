package test.utils;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

/**
 * An abstract test to extend.
 *
 * @author Laffini
 *
 */
public abstract class AbstractTest {

    /**
     * Setup.
     */
    @BeforeEach
    public void setUp() {
        // Initialise mocks created above
        MockitoAnnotations.openMocks(this);
        this.init();
    }

    /**
     * Any additional initialisation.
     */
    public abstract void init();

}

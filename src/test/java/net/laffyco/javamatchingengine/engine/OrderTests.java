package net.laffyco.javamatchingengine.engine;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Order tests.
 *
 * @author Laffini
 *
 */
public class OrderTests extends MatchingEngineTest {

    /**
     * Order amount.
     */
    private final int amt = 1;

    /**
     * Order price.
     */
    private final double price = 2;

    /**
     * Order side.
     */
    private final Side side = Side.BUY;

    /**
     * Compare two orders with different prices.
     */
    @Test
    @DisplayName("Compare two orders with different prices")
    public void comparePrice() {
        final double price2 = 3;

        final Order order1 = new Order(this.amt, this.price, this.side);
        final Order order2 = new Order(this.amt, price2, this.side);

        // Assert
        assertTrue(order1.compareTo(order2) < 0);
        assertTrue(order2.compareTo(order1) > 0);
    }

    /**
     * Compare orders with the same price, so therefore time is taken into
     * account.
     */
    @Test
    public void compareSamePrice() {
        final String id = "id";
        final Order order1 = new Order(this.amt, this.price, id, this.side,
                new Date(1626518162));
        final Order order2 = new Order(this.amt, this.price, id, this.side,
                new Date(1626515150));

        // Assert
        assertTrue(order1.compareTo(order2) > 0);
        assertTrue(order2.compareTo(order1) < 0);
    }
}

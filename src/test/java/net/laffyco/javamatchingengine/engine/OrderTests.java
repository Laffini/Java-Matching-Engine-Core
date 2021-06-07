package net.laffyco.javamatchingengine.engine;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * @author Laffini
 *
 */
public class OrderTests extends MatchingEngineTest {

    /**
     * Compare two orders with different prices.
     */
    @Test
    public void comparePrice() {
        final int amt = 1;
        final double price1 = 2;
        final double price2 = 3;
        final Side side = Side.BUY;

        final Order order1 = new Order(amt, price1, side);
        final Order order2 = new Order(amt, price2, side);

        // Assert
        assertTrue(order1.compareTo(order2) < 0);
        assertTrue(order2.compareTo(order1) > 0);
    }

}

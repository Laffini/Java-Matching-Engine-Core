package net.laffyco.javamatchingengine.core.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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

        final Order order1 = new Order.Builder(this.side).atPrice(this.price)
                .withAmount(this.amt).build();
        final Order order2 = new Order.Builder(this.side).atPrice(price2)
                .withAmount(this.amt).build();

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

        final Date date1 = new Date(1626518162);
        final Date date2 = new Date(1626515150);

        final Order order1 = new Order.Builder(this.side).withAmount(this.amt)
                .atPrice(this.price).withDateTime(date1).build();

        final Order order2 = new Order.Builder(this.side).withAmount(this.amt)
                .atPrice(this.price).withDateTime(date2).build();

        // Assert
        assertTrue(order1.compareTo(order2) > 0);
        assertTrue(order2.compareTo(order1) < 0);
    }

    /**
     * Test exception is thrown when an invalid price is entered.
     */
    @Test
    @DisplayName("An exception is thrown when an invalid order price is entered")
    public void invalidPrice() {
        final double invalidPrice = -1;

        try {
            new Order.Builder(Side.BUY).atPrice(invalidPrice).build();
            fail("Should have thrown an exception due to invalid order price");
        } catch (final IllegalArgumentException e) {
            assertEquals(e.getMessage(),
                    "Order prices must be greater than zero");

        }
    }

    /**
     * Test exception is thrown when an invalid amount is entered.
     */
    @Test
    @DisplayName("An exception is thrown when an invalid order amount is entered")
    public void invalidAmount() {
        final double invalidAmount = -1;

        try {
            new Order.Builder(Side.BUY).atPrice(this.price)
                    .withAmount(invalidAmount).build();
            fail("Should have thrown an exception due to invalid order amount");
        } catch (final IllegalArgumentException e) {
            assertEquals(e.getMessage(),
                    "Order amounts must be greater than zero");

        }
    }
}

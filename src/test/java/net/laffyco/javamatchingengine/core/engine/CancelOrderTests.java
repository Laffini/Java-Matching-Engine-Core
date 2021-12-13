package net.laffyco.javamatchingengine.core.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.annotation.Resource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

/**
 * Test the cancel order functionality.
 *
 * @author Laffini
 *
 */
public class CancelOrderTests extends MatchingEngineTest {

    /**
     * Order book.
     */
    @InjectMocks
    @Resource
    private OrderBook orderBook;

    /**
     * Attempt to cancel an order that doesn't exist.
     *
     */
    @Test
    @DisplayName("Attempt to cancel an order that doesn't exist")
    public void cancelNoCancellation() {
        assertEquals(this.orderBook.cancelOrder("test", Side.BUY), false);
        assertEquals(this.orderBook.cancelOrder("test", Side.SELL), false);
    }

    /**
     * Cancel a buy order, with side not given.
     *
     * @throws InterruptedException
     */
    @Test
    @DisplayName("Cancel a buy order with the side not given")
    public void cancelBuyOrder() throws InterruptedException {

        final int amt = 1;
        final double price = 1;
        final Side side = Side.BUY;
        // Create order.
        final Order order = new Order.Builder(side).atPrice(price)
                .withAmount(amt).build();

        // Process order
        this.orderBook.process(order);

        // Assert only 1 order in book.
        assertEquals(this.orderBook.getBuyOrders().size(), 1);

        // Cancel order
        assertEquals(this.orderBook.cancelOrder(order.getId(), side), true);
        assertEquals(this.orderBook.getBuyOrders().size(), 0);
    }

    /**
     * Cancel a buy order, with side given.
     *
     */
    @Test
    @DisplayName("Cancel a buy order")
    public void cancelBuyOrderSideGiven() {

        final int amt = 1;
        final double price = 1;
        final Side side = Side.BUY;
        // Create order.
        final Order order = new Order.Builder(side).atPrice(price)
                .withAmount(amt).build();

        // Process order
        this.orderBook.process(order);

        // Assert only 1 order in book.
        assertEquals(this.orderBook.getBuyOrders().size(), 1);

        // Cancel order
        assertEquals(this.orderBook.cancelOrder(order.getId(), side), true);
        assertEquals(this.orderBook.getBuyOrders().size(), 0);
    }

    /**
     * Cancel a sell order, with side not given.
     *
     * @throws InterruptedException
     */
    @Test
    @DisplayName("Cancel a sell order with the side not given")
    public void cancelSellOrder() throws InterruptedException {
        final int amt = 1;
        final double price = 1;
        final Side side = Side.SELL;
        // Create order.
        final Order order = new Order.Builder(side).atPrice(price)
                .withAmount(amt).build();

        // Process order
        this.orderBook.process(order);

        // Assert only 1 order in book.
        assertEquals(this.orderBook.getSellOrders().size(), 1);

        // Cancel order
        assertEquals(this.orderBook.cancelOrder(order.getId(), side), true);
        assertEquals(this.orderBook.getSellOrders().size(), 0);
    }

    /**
     * Cancel a sell order.
     *
     */
    @Test
    @DisplayName("Cancel a sell order")
    public void cancelSellOrderSideGiven() {
        final int amt = 1;
        final double price = 1;
        final Side side = Side.SELL;
        // Create order.
        final Order order = new Order.Builder(side).atPrice(price)
                .withAmount(amt).build();

        // Process order
        this.orderBook.process(order);

        // Assert only 1 order in book.
        assertEquals(this.orderBook.getSellOrders().size(), 1);

        // Cancel order
        assertEquals(this.orderBook.cancelOrder(order.getId(), side), true);
        assertEquals(this.orderBook.getSellOrders().size(), 0);
    }
}

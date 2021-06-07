package net.laffyco.javamatchingengine.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

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
    private final OrderBook orderBook = new OrderBook(new ArrayList<Order>(),
            new ArrayList<Order>());

    /**
     * Attempt to cancel an order that doesn't exist.
     *
     */
    @Test
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
    public void cancelBuyOrder() throws InterruptedException {

        final int amt = 1;
        final double price = 1;
        final Side side = Side.BUY;
        // Create order.
        final Order order = new Order(amt, price, side);

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
    public void cancelBuyOrderSideGiven() {

        final int amt = 1;
        final double price = 1;
        final Side side = Side.BUY;
        // Create order.
        final Order order = new Order(amt, price, side);

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
    public void cancelSellOrder() throws InterruptedException {
        final int amt = 1;
        final double price = 1;
        final Side side = Side.SELL;
        // Create order.
        final Order order = new Order(amt, price, side);

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
    public void cancelSellOrderSideGiven() {
        final int amt = 1;
        final double price = 1;
        final Side side = Side.SELL;
        // Create order.
        final Order order = new Order(amt, price, side);

        // Process order
        this.orderBook.process(order);

        // Assert only 1 order in book.
        assertEquals(this.orderBook.getSellOrders().size(), 1);

        // Cancel order
        assertEquals(this.orderBook.cancelOrder(order.getId(), side), true);
        assertEquals(this.orderBook.getSellOrders().size(), 0);
    }

}

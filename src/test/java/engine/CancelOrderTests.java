/**
 *
 */
package engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

/**
 * @author Laffini
 *
 */
public class CancelOrderTests extends MatchingEngineTest {

    private final OrderBook orderBook = new OrderBook(new ArrayList<Order>(), new ArrayList<Order>());

    /**
     * Attempt to cancel an order that doesn't exist.
     *
     * @throws InterruptedException
     */
    @Test
    public void cancel_NoCancellation() throws InterruptedException {

        assertEquals(this.orderBook.cancelOrder("test"), false);

    }

    /**
     * Cancel a buy order, with side not given.
     *
     * @throws InterruptedException
     */
    @Test
    public void cancel_BuyOrder() throws InterruptedException {

        final int amt = 1;
        final double price = 1;
        final String id = "test";
        final Side side = Side.BUY;
        // Create order.
        final Order order = new Order(amt, price, id, side);

        // Process order
        this.orderBook.process(order);

        // Assert only 1 order in book.
        assertEquals(this.orderBook.getBuyOrders().size(), 1);

        // Cancel order
        assertEquals(this.orderBook.cancelOrder(id), true);
        assertEquals(this.orderBook.getBuyOrders().size(), 0);

    }

    /**
     * Cancel a buy order, with side given.
     *
     * @throws InterruptedException
     */
    @Test
    public void cancel_BuyOrderSideGiven() throws InterruptedException {

        final int amt = 1;
        final double price = 1;
        final String id = "test";
        final Side side = Side.BUY;
        // Create order.
        final Order order = new Order(amt, price, id, side);

        // Process order
        this.orderBook.process(order);

        // Assert only 1 order in book.
        assertEquals(this.orderBook.getBuyOrders().size(), 1);

        // Cancel order
        assertEquals(this.orderBook.cancelOrder(id, side), true);
        assertEquals(this.orderBook.getBuyOrders().size(), 0);

    }

    /**
     * Cancel a sell order, with side not given.
     *
     * @throws InterruptedException
     */
    @Test
    public void cancel_SellOrder() throws InterruptedException {
        final int amt = 1;
        final double price = 1;
        final String id = "test";
        final Side side = Side.SELL;
        // Create order.
        final Order order = new Order(amt, price, id, side);

        // Process order
        this.orderBook.process(order);

        // Assert only 1 order in book.
        assertEquals(this.orderBook.getSellOrders().size(), 1);

        // Cancel order
        assertEquals(this.orderBook.cancelOrder(id), true);
        assertEquals(this.orderBook.getSellOrders().size(), 0);
    }

    /**
     * Cancel a sell order, with side given.
     *
     * @throws InterruptedException
     */
    @Test
    public void cancel_SellOrderSideGiven() throws InterruptedException {
        final int amt = 1;
        final double price = 1;
        final String id = "test";
        final Side side = Side.SELL;
        // Create order.
        final Order order = new Order(amt, price, id, side);

        // Process order
        this.orderBook.process(order);

        // Assert only 1 order in book.
        assertEquals(this.orderBook.getSellOrders().size(), 1);

        // Cancel order
        assertEquals(this.orderBook.cancelOrder(id, side), true);
        assertEquals(this.orderBook.getSellOrders().size(), 0);
    }

    /**
     * Attempt to cancel an order that doesn't exist with a side given.
     * 
     * @throws InterruptedException
     */
    @Test
    public void cancel_SideGivenNoOrder() throws InterruptedException {

        assertEquals(this.orderBook.cancelOrder("invalidId", Side.BUY), false);

    }

}

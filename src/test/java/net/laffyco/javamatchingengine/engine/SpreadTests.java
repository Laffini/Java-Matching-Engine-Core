package net.laffyco.javamatchingengine.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;

/**
 * Test the getSpread() functionality.
 *
 * @author Laffini
 */
public class SpreadTests extends MatchingEngineTest {

    /**
     * Order book.
     */
    private final OrderBook orderBook = new OrderBook(new ArrayList<Order>(),
            new ArrayList<Order>());

    @Test
    void spreadTwoOrders() {
        final Order[] orders = {
                new Order(2, 2.50, "sellOrder", Side.SELL, new Date()),
                new Order(2, 2, "buyOrder", Side.BUY, new Date()) };
        this.addOrders(this.orderBook, orders);

        final double expectedSpread = 0.5;

        assertEquals(expectedSpread, this.orderBook.getSpread());
    }

    @Test
    void spreadMultipleOrders() {
        final Order[] orders = {
                new Order(2, 2.50, "sellOrder", Side.SELL, new Date()),
                new Order(2, 2.75, "sellOrder", Side.SELL, new Date()),
                new Order(2, 2, "buyOrder", Side.BUY, new Date()),
                new Order(2, 1.5, "secondBuyOrder", Side.BUY, new Date()) };
        this.addOrders(this.orderBook, orders);

        final double expectedSpread = 0.5;

        assertEquals(expectedSpread, this.orderBook.getSpread());
    }

    @Test
    void spreadNoOrders() {
        assertEquals(this.orderBook.getSpread(), 0);
    }

}

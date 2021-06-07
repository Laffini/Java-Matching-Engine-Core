package net.laffyco.javamatchingengine.engine;

/**
 * Common test functionality.
 *
 * @author Laffini
 *
 */
public abstract class MatchingEngineTest {

    /**
     * Add an array of orders to the order book.
     *
     * @param orderBook
     * @param orders
     */
    public void addOrders(final OrderBook orderBook, final Order[] orders) {
        for (int i = 0; i < orders.length; i++) {
            orderBook.process(orders[i]);
        }

    }
}

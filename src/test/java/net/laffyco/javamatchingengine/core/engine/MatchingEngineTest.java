package net.laffyco.javamatchingengine.core.engine;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

import net.laffyco.javamatchingengine.core.engine.Order;
import net.laffyco.javamatchingengine.core.engine.OrderBook;

/**
 * Common test functionality.
 *
 * @author Laffini
 *
 */
public abstract class MatchingEngineTest {

    /**
     * Test setup.
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

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

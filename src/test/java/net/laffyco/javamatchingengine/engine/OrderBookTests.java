package net.laffyco.javamatchingengine.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

/**
 * Order book tests.
 *
 * @author Laffini
 *
 */
public class OrderBookTests extends MatchingEngineTest {

    /**
     * Array of orders.
     */
    private final Order[] orders = { new Order(3, 2, Side.BUY),
            new Order(3, 2, Side.SELL) };

    /**
     * Test OrderBook.
     */
    @InjectMocks
    @Resource
    private OrderBook orderBook;

//    /**
//     * Test setup.
//     */
//    @BeforeEach
//    public void setUp() {
//        this.orderBook = new OrderBook(new ArrayList<Order>(),
//                new ArrayList<Order>());
//    }

    /**
     * Add a buy order, then add a matching sell order.
     */
    @Test
    public void buyThenSell() {

        // Add buy order
        this.orderBook.process(this.orders[0]);

        // There should only be 1 buy order.
        assertEquals(this.orderBook.getBuyOrders().size(), 1);

        // There should not be any sell orders
        assertEquals(this.orderBook.getSellOrders().size(), 0);

        // Add sell order
        final List<Trade> trades = this.orderBook.process(this.orders[1]);

        // There should not be any buy or sell orders
        assertEquals(this.orderBook.getSellOrders().size(), 0);
        assertEquals(this.orderBook.getBuyOrders().size(), 0);

        // There should be only 1 trade
        assertEquals(trades.size(), 1);

        // Assert the trade details
        final Trade trade = trades.get(0);
        assertEquals(trade.getPrice(), this.orders[1].getPrice());
        assertEquals(trade.getAmount(), this.orders[1].getAmount());

        // Assert last trade price
        assertEquals(this.orderBook.getLastSalePrice(), 2);
    }

    /**
     * Add a sell order, then add a buy order.
     */
    @Test
    public void sellThenBuy() {

        // Add sell order
        this.orderBook.process(this.orders[1]);

        // There should only be 1 sell order.
        assertEquals(this.orderBook.getSellOrders().size(), 1);

        // There should not be any buy orders
        assertEquals(this.orderBook.getBuyOrders().size(), 0);

        // Add buy order
        final List<Trade> trades = this.orderBook.process(this.orders[0]);

        // There should not be any buy or sell orders
        assertEquals(this.orderBook.getSellOrders().size(), 0);
        assertEquals(this.orderBook.getBuyOrders().size(), 0);

        // There should be only 1 trade
        assertEquals(trades.size(), 1);

        // Assert the trade details
        final Trade trade = trades.get(0);
        assertEquals(trade.getPrice(), this.orders[0].getPrice());
        assertEquals(trade.getAmount(), this.orders[0].getAmount());

        // Assert last trade price
        assertEquals(this.orderBook.getLastSalePrice(), 2);
    }

    /**
     * Find order tests.
     */
    @Test
    public void findOrder() {

        // Can't find an order that hasn't been added to the book.
        assertEquals(this.orderBook.findOrder(this.orders[1].getId(),
                this.orders[1].getSide()), null);
        assertEquals(this.orderBook.findOrder(this.orders[0].getId(),
                this.orders[0].getSide()), null);

        // Add sell order
        this.orderBook.process(this.orders[1]);

        // Can find the order now.
        assertEquals(this.orderBook.findOrder(this.orders[1].getId(),
                this.orders[1].getSide()), this.orders[1]);

        // Add & find buy order.
        this.orderBook.process(this.orders[0]);
        // Add twice as first is matched with previous sell.
        this.orderBook.process(this.orders[0]);
        assertEquals(this.orderBook.findOrder(this.orders[0].getId(),
                this.orders[0].getSide()), this.orders[0]);
    }

    /**
     * Test that no orders are cancelled when an invalid side is provided.
     */
    @Test
    public void cancelTestInvalidSide() {

        final String orderId = "";

        final boolean result = this.orderBook.cancelOrder(orderId, null);
        assertFalse(result);
    }
}

package net.laffyco.javamatchingengine.core.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.jupiter.api.DisplayName;
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
    private final Order[] orders = {new Order(3, 2, Side.BUY),
            new Order(3, 2, Side.SELL)};

    /**
     * Test OrderBook.
     */
    @InjectMocks
    @Resource
    private OrderBook orderBook;

    /**
     * Add a buy order, then add a matching sell order.
     */
    @Test
    @DisplayName("Add a buy order, then add a matching sell order")
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
    @DisplayName("Add a sell order, then add a matching buy order")
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
    @DisplayName("Find orders")
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
    @DisplayName("No orders are cancelled when an invalid side is provided")
    public void cancelTestInvalidSide() {

        final String orderId = "";

        final boolean result = this.orderBook.cancelOrder(orderId, null);
        assertFalse(result);
    }

    /**
     * A buy order that fills two sell orders.
     */
    @Test
    @DisplayName("A buy order that fills two sell orders")
    public void buyPartialFill() {

        // Add two sell orders.
        this.orderBook.process(this.orders[1]);
        this.orderBook.process(this.orders[1]);

        // Modify buy order to be twice the amount.
        final Order buyOrder = this.orders[0];
        buyOrder.setAmount(buyOrder.getAmount() * 2);

        // Add the buy order.
        final List<Trade> trades = this.orderBook.process(buyOrder);

        // Two trades should have taken place.
        assertTrue(trades.size() == 2);

        // Order book should be empty.
        assertTrue(this.orderBook.getBuyOrders().isEmpty());
        assertTrue(this.orderBook.getSellOrders().isEmpty());

        // The trades match the expected amt and price.
        for (final Trade trade : trades) {
            assertTrue(trade.getAmount() == buyOrder.getAmount());
            assertTrue(trade.getPrice() == buyOrder.getPrice());
        }
    }

    /**
     * A buy order that fills two sell orders.
     */
    @Test
    @DisplayName("A sell order that fills two buy orders")
    public void sellPartialFill() {

        // Add two buy orders.
        this.orderBook.process(this.orders[0]);
        this.orderBook.process(this.orders[0]);

        // Modify sell order to be twice the amount.
        final Order sellOrder = this.orders[1];
        sellOrder.setAmount(sellOrder.getAmount() * 2);

        // Add the buy order.
        final List<Trade> trades = this.orderBook.process(sellOrder);

        // Two trades should have taken place.
        assertTrue(trades.size() == 2);

        // Order book should be empty.
        assertTrue(this.orderBook.getBuyOrders().isEmpty());
        assertTrue(this.orderBook.getSellOrders().isEmpty());

        // The trades match the expected amt and price.
        for (final Trade trade : trades) {
            assertTrue(trade.getAmount() == sellOrder.getAmount());
            assertTrue(trade.getPrice() == sellOrder.getPrice());
        }
    }
}

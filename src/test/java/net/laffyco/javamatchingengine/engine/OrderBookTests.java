package net.laffyco.javamatchingengine.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

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
     * Add a buy order, then add a matching sell order.
     */
    @Test
    public void buyThenSell() {

        final OrderBook orderBook = new OrderBook(new ArrayList<Order>(),
                new ArrayList<Order>());

        // Add buy order
        orderBook.process(this.orders[0]);

        // There should only be 1 buy order.
        assertEquals(orderBook.getBuyOrders().size(), 1);

        // There should not be any sell orders
        assertEquals(orderBook.getSellOrders().size(), 0);

        // Add sell order
        final ArrayList<Trade> trades = orderBook.process(this.orders[1]);

        // There should not be any buy or sell orders
        assertEquals(orderBook.getSellOrders().size(), 0);
        assertEquals(orderBook.getBuyOrders().size(), 0);

        // There should be only 1 trade
        assertEquals(trades.size(), 1);

        // Assert the trade details
        final Trade trade = trades.get(0);
        assertEquals(trade.getPrice(), this.orders[1].getPrice());
        assertEquals(trade.getAmount(), this.orders[1].getAmount());

        // Assert last trade price
        assertEquals(orderBook.getLastSalePrice(), 2);
    }

    /**
     * Add a sell order, then add a buy order.
     */
    @Test
    public void sellThenBuy() {

        final OrderBook orderBook = new OrderBook(new ArrayList<Order>(),
                new ArrayList<Order>());

        // Add sell order
        orderBook.process(this.orders[1]);

        // There should only be 1 sell order.
        assertEquals(orderBook.getSellOrders().size(), 1);

        // There should not be any buy orders
        assertEquals(orderBook.getBuyOrders().size(), 0);

        // Add buy order
        final ArrayList<Trade> trades = orderBook.process(this.orders[0]);

        // There should not be any buy or sell orders
        assertEquals(orderBook.getSellOrders().size(), 0);
        assertEquals(orderBook.getBuyOrders().size(), 0);

        // There should be only 1 trade
        assertEquals(trades.size(), 1);

        // Assert the trade details
        final Trade trade = trades.get(0);
        assertEquals(trade.getPrice(), this.orders[0].getPrice());
        assertEquals(trade.getAmount(), this.orders[0].getAmount());

        // Assert last trade price
        assertEquals(orderBook.getLastSalePrice(), 2);
    }
}

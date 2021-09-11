package net.laffyco.javamatchingengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import net.laffyco.javamatchingengine.engine.Order;
import net.laffyco.javamatchingengine.engine.OrderBook;
import net.laffyco.javamatchingengine.engine.Side;
import net.laffyco.javamatchingengine.engine.Trade;
import test.utils.AbstractTest;

/**
 * Tests for the OrderController class.
 *
 * @author Laffini
 *
 */
public class OrderControllerTests extends AbstractTest {

    /**
     * Mock order book.
     */
    @Mock
    private OrderBook orderBook;

    /**
     * Controller under test.
     */
    @InjectMocks
    @Resource
    private OrderController controller;

    /**
     * Test data id.
     */
    private final String id = "id";

    /**
     * Test data side.
     */
    private final Side side = Side.BUY;

    /** Create a mock order to return. */
    private final Order mockOrder = Mockito.mock(Order.class);

    /**
     * Test data amount.
     */
    private final double amt = 25.0;

    @Override
    public final void init() {
        Mockito.when(this.orderBook.findOrder(this.id, this.side))
                .thenReturn(this.mockOrder);
    }

    /**
     * Get orders test.
     */
    @Test
    @DisplayName("Get the orders")
    public void getOrders() {
        final Map<String, List<Order>> result = this.controller.getOrders();

        Mockito.verify(this.orderBook).getBuyOrders();
        Mockito.verify(this.orderBook).getSellOrders();

        assertTrue(result.containsKey("buy"));
        assertTrue(result.containsKey("sell"));
    }

    /**
     * Get order by ID test.
     */
    @Test
    @DisplayName("Get an order by ID")
    public void getOrderById() {
        final Map<String, Order> result = this.controller.getOrder(this.id,
                this.side);
        assertTrue(result.containsKey("order"));
        assertEquals(result.get("order"), this.mockOrder);
    }

    /**
     * Add order test.
     */
    @Test
    @DisplayName("Add an order")
    public void addOrder() {
        final double price = 25;

        final List<Trade> trades = new ArrayList<Trade>();
        Mockito.when(this.orderBook.process(Mockito.any())).thenReturn(trades);

        final Map<String, Object> result = this.controller.addOrder(this.side,
                this.amt, price);

        assertTrue(result.containsKey(this.id));
        assertTrue(result.containsKey("trades"));
        assertEquals(result.get("trades"), trades);
    }

    /**
     * Delete an order.
     */
    @Test
    @DisplayName("Delete an order")
    public void deleteOrder() {

        Mockito.when(this.orderBook.cancelOrder(this.id, this.side))
                .thenReturn(true);

        final Map<String, Object> result = this.controller.deleteOrder(this.id,
                this.side);

        assertTrue((boolean) result.get("order_deleted"));

        Mockito.verify(this.orderBook).cancelOrder(this.id, this.side);
    }

    /**
     * Update an order.
     */
    @Test
    @DisplayName("Update an order")
    public void updateOrder() {
        final double price = 35.0;
        final Side newSide = Side.SELL;

        final Map<String, Object> result = this.controller.updateOrder(this.id,
                this.side, Optional.of(this.amt), Optional.of(price),
                Optional.of(newSide));

        assertTrue((boolean) result.get("updated"));
        Mockito.verify(this.mockOrder).setAmount(this.amt);
        Mockito.verify(this.mockOrder).setPrice(price);
        Mockito.verify(this.mockOrder).setSide(newSide);
    }

    /**
     * Update an order with one parameter.
     */
    @Test
    @DisplayName("Update an order with one parameter")
    public void updateOrder1Param() {

        final Map<String, Object> result = this.controller.updateOrder(this.id,
                this.side, Optional.of(this.amt), Optional.empty(),
                Optional.empty());

        assertTrue((boolean) result.get("updated"));
        Mockito.verify(this.mockOrder).setAmount(this.amt);
        Mockito.verify(this.mockOrder, Mockito.times(0))
                .setPrice(Mockito.anyDouble());
        Mockito.verify(this.mockOrder, Mockito.times(0))
                .setSide(Mockito.any(Side.class));
    }
}

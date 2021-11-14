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
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

import net.laffyco.javamatchingengine.engine.Order;
import net.laffyco.javamatchingengine.engine.OrderBook;
import net.laffyco.javamatchingengine.engine.Side;
import net.laffyco.javamatchingengine.engine.Trade;
import net.laffyco.javamatchingengine.events.OrderAddedEvent;
import net.laffyco.javamatchingengine.events.OrderMatchedEvent;
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
     * Event publisher.
     */
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * Controller under test.
     */
    @InjectMocks
    @Resource
    private OrderController controller;

    /**
     * Key captor.
     */
    @Captor
    private ArgumentCaptor<ApplicationEvent> captor;

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

    /**
     * Test data price.
     */
    private final double price = 25;

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
        final List<Trade> trades = new ArrayList<Trade>();
        Mockito.when(this.orderBook.process(Mockito.any())).thenReturn(trades);

        final Map<String, Object> result = this.controller.addOrder(this.side,
                this.amt, this.price);

        assertTrue(result.containsKey(this.id));
        assertTrue(result.containsKey("trades"));
        assertEquals(result.get("trades"), trades);
    }

    /**
     * Checking that the correct events are published.
     */
    @Test
    @DisplayName("Check that appropriate events are published")
    public void events() {
        final List<Trade> trades = new ArrayList<Trade>() {
            {
                this.add(null);
            }
        };
        Mockito.when(this.orderBook.process(Mockito.any())).thenReturn(trades);

        this.controller.addOrder(this.side, this.amt, this.price);

        Mockito.verify(this.applicationEventPublisher, Mockito.times(2))
                .publishEvent(this.captor.capture());

        assertTrue(
                this.captor.getAllValues().get(0) instanceof OrderAddedEvent);
        assertTrue(
                this.captor.getAllValues().get(1) instanceof OrderMatchedEvent);
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
        final Side newSide = Side.SELL;

        final Map<String, Object> result = this.controller.updateOrder(this.id,
                this.side, Optional.of(this.amt), Optional.of(this.price),
                Optional.of(newSide));

        assertTrue((boolean) result.get("updated"));
        Mockito.verify(this.mockOrder).setAmount(this.amt);
        Mockito.verify(this.mockOrder).setPrice(this.price);
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

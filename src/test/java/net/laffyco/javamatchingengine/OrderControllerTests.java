package net.laffyco.javamatchingengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import net.laffyco.javamatchingengine.engine.Order;
import net.laffyco.javamatchingengine.engine.OrderBook;
import net.laffyco.javamatchingengine.engine.Side;
import net.laffyco.javamatchingengine.engine.Trade;

/**
 * Tests for the OrderController class.
 *
 * @author Laffini
 *
 */
public class OrderControllerTests {

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
     * Setup.
     */
    @BeforeEach
    public void setUp() {
        // Initialise mocks created above
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Get orders test.
     */
    @Test
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
    public void getOrderById() {
        final Order found = Mockito.mock(Order.class);
        final String id = "testId";
        final Side side = Side.BUY;
        Mockito.when(this.orderBook.findOrder(id, side)).thenReturn(found);
        final Map<String, Order> result = this.controller.getOrder(id, side);
        assertTrue(result.containsKey("order"));
        assertEquals(result.get("order"), found);
    }

    /**
     * Add order test.
     */
    @Test
    public void addOrder() {
        final Side side = Side.BUY;
        final double amt = 10;
        final double price = 25;

        final List<Trade> trades = new ArrayList<Trade>();
        Mockito.when(this.orderBook.process(Mockito.any())).thenReturn(trades);

        final Map<String, Object> result = this.controller.addOrder(side, amt,
                price);

        assertTrue(result.containsKey("id"));
        assertTrue(result.containsKey("trades"));
        assertEquals(result.get("trades"), trades);
    }

}

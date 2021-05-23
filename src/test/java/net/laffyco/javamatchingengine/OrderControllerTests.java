package net.laffyco.javamatchingengine;

import static org.junit.jupiter.api.Assertions.assertTrue;

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

}

package net.laffyco.javamatchingengine.core.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import net.laffyco.javamatchingengine.core.api.SpreadInterface;
import net.laffyco.javamatchingengine.core.engine.OrderBook;
import test.utils.AbstractTest;

/**
 * @author Laffini
 *
 */
public class SpreadInterfaceTests extends AbstractTest {

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
    private SpreadInterface controller;

    @Override
    public void init() {
        // Left blank intentionally.
    }

    /**
     * Get spread.
     */
    @Test
    @DisplayName("Get the spread")
    public void getSpread() {
        final double spread = 3.0;
        Mockito.when(this.orderBook.getSpread()).thenReturn(spread);
        final Map<String, Double> result = this.controller.getSpread();
        assertEquals(spread, result.get("spread"));
    }

}

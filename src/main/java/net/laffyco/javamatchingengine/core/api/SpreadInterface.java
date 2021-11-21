package net.laffyco.javamatchingengine.core.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.laffyco.javamatchingengine.core.engine.OrderBook;

/**
 * Spread controller.
 *
 * @author Laffini
 *
 */
@Service
public class SpreadInterface {

    /**
     * Order book.
     */
    @Autowired
    private OrderBook orderBook;

    /**
     * Retrieve the order book spread.
     *
     * @return spread
     */
    public Map<String, Double> getSpread() {
        final Map<String, Double> response = new HashMap<>();
        final double spread = this.orderBook.getSpread();
        response.put("spread", spread);
        return response;
    }
}

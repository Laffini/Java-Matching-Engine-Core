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
public class SpreadInterface implements ISpreadInterface {

    /**
     * Order book.
     */
    @Autowired
    private OrderBook orderBook;

    @Override
    public final Map<String, Double> getSpread() {
        final Map<String, Double> response = new HashMap<>();
        final double spread = this.orderBook.getSpread();
        response.put("spread", spread);
        return response;
    }
}

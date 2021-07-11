package net.laffyco.javamatchingengine;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.laffyco.javamatchingengine.engine.OrderBook;

/**
 * Spread controller.
 *
 * @author Laffini
 *
 */
@RestController
@RequestMapping("/spread")
public class SpreadController {

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
    @GetMapping("/")
    public Map<String, Double> getSpread() {
        final Map<String, Double> response = new HashMap<>();
        final double spread = this.orderBook.getSpread();
        response.put("spread", spread);
        return response;
    }
}

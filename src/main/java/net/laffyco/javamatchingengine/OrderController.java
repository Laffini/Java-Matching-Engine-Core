package net.laffyco.javamatchingengine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.laffyco.javamatchingengine.engine.Order;
import net.laffyco.javamatchingengine.engine.OrderBook;

/**
 * Controller for orders.
 *
 * @author Laffini
 *
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    /**
     * Order book.
     */
    @Autowired
    private OrderBook orderBook;

    /**
     * Retrieve orders.
     *
     * @return orders
     */
    @GetMapping("/")
    public Map<String, List<Order>> getOrders() {
        final Map<String, List<Order>> response = new HashMap<>();
        response.put("buy", this.orderBook.getBuyOrders());
        response.put("sell", this.orderBook.getSellOrders());
        return response;
    }

    // POST add a order

    // PUT update an order

    // Delete, delete an order.

}

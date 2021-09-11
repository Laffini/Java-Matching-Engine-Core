package net.laffyco.javamatchingengine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.laffyco.javamatchingengine.engine.Order;
import net.laffyco.javamatchingengine.engine.OrderBook;
import net.laffyco.javamatchingengine.engine.Side;

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

    /**
     * Retrieve an order by ID.
     *
     * @param id
     * @param side
     * @return order (or null if not found)
     */
    @GetMapping("/{id}")
    public Map<String, Order> getOrder(@PathVariable final String id,
            @RequestParam("side") final Side side) {
        final Map<String, Order> response = new HashMap<>();

        response.put("order", this.orderBook.findOrder(id, side));

        return response;
    }

    /**
     * Add an order.
     *
     * @param side
     * @param amount
     * @param price
     * @return order id
     */
    @PostMapping("/")
    public Map<String, Object> addOrder(@RequestParam("side") final Side side,
            @RequestParam("amount") final double amount,
            @RequestParam("price") final double price) {
        final Map<String, Object> response = new HashMap<>();

        final Order order = new Order(amount, price, side);

        response.put("id", order.getId());
        response.put("trades", this.orderBook.process(order));

        return response;
    }

    /**
     * Delete an order.
     *
     * @param id
     * @param side
     * @return whether an order was deleted or not
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteOrder(@PathVariable final String id,
            @RequestParam("side") final Side side) {
        final Map<String, Object> response = new HashMap<>();

        final boolean result = this.orderBook.cancelOrder(id, side);

        response.put("order_deleted", result);

        return response;
    }

    /**
     * Update an order.
     *
     * @param id
     * @param side
     * @param newAmount
     * @param newPrice
     * @param newSide
     * @return whether an order has been updated or not
     */
    @PutMapping("/{id}")
    public Map<String, Object> updateOrder(@PathVariable final String id,
            @RequestParam("side") final Side side,
            @RequestParam("newAmount") final Optional<Double> newAmount,
            @RequestParam("newPrice") final Optional<Double> newPrice,
            @RequestParam("newSide") final Optional<Side> newSide) {

        final Map<String, Object> response = new HashMap<>();

        boolean isUpdated = false;

        final Order toUpdate = this.orderBook.findOrder(id, side);

        if (newAmount.isPresent()) {
            // Update amount.
            toUpdate.setAmount(newAmount.get());
            isUpdated = true;
        }

        if (newPrice.isPresent()) {
            // Update price.
            toUpdate.setPrice(newPrice.get());
            isUpdated = true;
        }

        if (newSide.isPresent()) {
            // Update side.
            toUpdate.setSide(newSide.get());
            isUpdated = true;
        }

        response.put("updated", isUpdated);

        return response;
    }
}

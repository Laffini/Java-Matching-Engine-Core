package net.laffyco.javamatchingengine.core.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import net.laffyco.javamatchingengine.core.engine.Order;
import net.laffyco.javamatchingengine.core.engine.OrderBook;
import net.laffyco.javamatchingengine.core.engine.Side;
import net.laffyco.javamatchingengine.core.engine.Trade;
import net.laffyco.javamatchingengine.core.events.OrderAddedEvent;
import net.laffyco.javamatchingengine.core.events.OrderMatchedEvent;

/**
 * Controller for orders.
 *
 * @author Laffini
 *
 */
@Service
public class OrderInterface {

    /**
     * Order book.
     */
    @Autowired
    private OrderBook orderBook;

    /**
     * Event publisher.
     */
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * Retrieve orders.
     *
     * @return orders
     */
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
    public Map<String, Order> getOrder(final String id, final Side side) {
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
    public Map<String, Object> addOrder(final Side side, final double amount,
            final double price) {
        final Map<String, Object> response = new HashMap<>();

        final Order order = new Order(amount, price, side);

        final List<Trade> trades = this.orderBook.process(order);

        response.put("id", order.getId());
        response.put("trades", trades);
        this.applicationEventPublisher
                .publishEvent(new OrderAddedEvent(this, order));

        if (!trades.isEmpty()) {
            this.applicationEventPublisher
                    .publishEvent(new OrderMatchedEvent(this, trades));
        }

        return response;
    }

    /**
     * Delete an order.
     *
     * @param id
     * @param side
     * @return whether an order was deleted or not
     */
    public Map<String, Object> deleteOrder(final String id, final Side side) {
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
    public Map<String, Object> updateOrder(final String id, final Side side,
            final Optional<Double> newAmount, final Optional<Double> newPrice,
            final Optional<Side> newSide) {

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

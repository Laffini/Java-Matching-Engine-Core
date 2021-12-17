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
public class OrderInterface implements IOrderInterface {

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

    @Override
    public final Map<String, List<Order>> getOrders() {
        final Map<String, List<Order>> response = new HashMap<>();
        response.put("buy", this.orderBook.getBuyOrders());
        response.put("sell", this.orderBook.getSellOrders());
        return response;
    }

    @Override
    public final Map<String, Order> getOrder(final String id, final Side side) {
        final Map<String, Order> response = new HashMap<>();

        response.put("order", this.orderBook.findOrder(id, side));

        return response;
    }

    @Override
    public final Map<String, Object> addOrder(final Side side,
            final double amount, final double price) {
        final Map<String, Object> response = new HashMap<>();

        final Order order = new Order.Builder(side).withAmount(amount)
                .atPrice(price).build();

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

    @Override
    public final Map<String, Object> deleteOrder(final String id,
            final Side side) {
        final Map<String, Object> response = new HashMap<>();

        final boolean result = this.orderBook.cancelOrder(id, side);

        response.put("order_deleted", result);

        return response;
    }

    @Override
    public final Map<String, Object> updateOrder(final String id,
            final Side side, final Optional<Double> newAmount,
            final Optional<Double> newPrice, final Optional<Side> newSide) {

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

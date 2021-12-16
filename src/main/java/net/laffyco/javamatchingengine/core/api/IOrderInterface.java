package net.laffyco.javamatchingengine.core.api;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import net.laffyco.javamatchingengine.core.engine.Order;
import net.laffyco.javamatchingengine.core.engine.Side;

/**
 * Interface definition for orders.
 *
 * @author Laffini
 *
 */
public interface IOrderInterface {

    /**
     * Retrieve all orders.
     *
     * @return A map of orders.
     */
    Map<String, List<Order>> getOrders();

    /**
     * Retrieve an order by ID.
     *
     * @param id
     * @param side
     * @return order
     */
    Map<String, Order> getOrder(String id, Side side);

    /**
     * Add an order.
     *
     * @param side
     * @param amount
     * @param price
     * @return order id
     */
    Map<String, Object> addOrder(Side side, double amount, double price);

    /**
     * Delete an order.
     *
     * @param id
     * @param side
     * @return whether an order was deleted or not
     */
    Map<String, Object> deleteOrder(String id, Side side);

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
    Map<String, Object> updateOrder(String id, Side side,
            Optional<Double> newAmount, Optional<Double> newPrice,
            Optional<Side> newSide);

}

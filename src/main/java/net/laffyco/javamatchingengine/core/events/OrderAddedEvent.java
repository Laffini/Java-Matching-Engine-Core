package net.laffyco.javamatchingengine.core.events;

import org.springframework.context.ApplicationEvent;

import net.laffyco.javamatchingengine.core.engine.Order;

/**
 * Event for when an order is added to the engine.
 *
 * @author Laffini
 *
 */
public class OrderAddedEvent extends ApplicationEvent {

    /**
     * The order added.
     */
    private final Order order;

    /**
     * Constructor.
     *
     * @param source
     * @param pOrder
     */
    public OrderAddedEvent(final Object source, final Order pOrder) {
        super(source);
        this.order = pOrder;
    }

    /**
     * Get order.
     *
     * @return the order
     */
    public Order getOrder() {
        return this.order;
    }

}

package net.laffyco.javamatchingengine.core.events;

import java.util.List;

import org.springframework.context.ApplicationEvent;

import net.laffyco.javamatchingengine.core.engine.Trade;

/**
 * Order matched event.
 *
 * @author Laffini
 *
 */
public class OrderMatchedEvent extends ApplicationEvent {

    /**
     * Completed trades.
     */
    private final List<Trade> trades;

    /**
     * Constructor.
     *
     * @param source
     * @param pTrades
     */
    public OrderMatchedEvent(final Object source, final List<Trade> pTrades) {
        super(source);
        this.trades = pTrades;
    }

}

package net.laffyco.javamatchingengine.core.api;

import java.util.Map;

/**
 * Interface definition for spread.
 *
 * @author Laffini
 *
 */
public interface ISpreadInterface {

    /**
     * Retrieve the order book spread.
     *
     * @return spread
     */
    Map<String, Double> getSpread();

}

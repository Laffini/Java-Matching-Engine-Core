package engine;

import java.util.HashMap;

/**
 * @author Laffini
 *
 */
public class OrderBook {

    private final HashMap<String, String> bids;
    private final HashMap<String, String> asks;
    private final HashMap<String, String> orderIdMap;

    /**
     * Create an instance of OrderBook
     *
     * @param bids
     * @param asks
     * @param orderIdMap
     */
    public OrderBook() {
        this.bids = new HashMap<String, String>(); // TODO: Change to used datatype

        this.asks = new HashMap<String, String>(); // TODO: Change to used datatype

        this.orderIdMap = new HashMap<String, String>(); // TODO: Change to used datatype

    }

    /**
     * @return the bids
     */
    public synchronized HashMap<String, String> getBids() {
        return this.bids;
    }

    /**
     * @return the asks
     */
    public synchronized HashMap<String, String> getAsks() {
        return this.asks;
    }

    /**
     * @return the orderIdMap
     */
    public synchronized HashMap<String, String> getOrderIdMap() {
        return this.orderIdMap;
    }

}

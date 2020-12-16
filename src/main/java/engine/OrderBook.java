package engine;

import java.util.ArrayList;

/**
 * @author Laffini
 *
 */
public class OrderBook {

    private ArrayList<Order> buyOrders;
    private ArrayList<Order> sellOrders;

    /**
     * Create an instance of OrderBook.
     *
     * @param buyOrders
     * @param sellOrders
     */
    public OrderBook(final ArrayList<Order> buyOrders, final ArrayList<Order> sellOrders) {
        super();
        this.buyOrders = buyOrders;
        this.sellOrders = sellOrders;
    }

    /**
     * Process an order and return the trades generated before adding the remaining
     * amount to the market.
     *
     * @param Order
     */
    public synchronized ArrayList<Trade> process(final Order order) {

        if (order.getSide() == Side.BUY) {
            return this.processLimitBuy(order);
        } else {
            return this.processLimitSell(order);
        }

    }

    /**
     * Process limit buy.
     *
     * @param order
     * @return Trades.
     */
    public synchronized ArrayList<Trade> processLimitBuy(final Order order) {
        final ArrayList<Trade> trades = new ArrayList<Trade>();

        final int n = this.sellOrders.size();

        // Check if at least one matching order.
        if (n != 0 || this.sellOrders.get(n - 1).getPrice() <= order.getPrice()) {

            // Traverse matching orders
            for (int i = n - 1; i >= 0; i++) {
                final Order sellOrder = this.sellOrders.get(i);
                if (sellOrder.getPrice() > order.getPrice()) {
                    break;
                }
                // Fill entire order.
                if (sellOrder.getAmount() >= order.getAmount()) {
                    trades.add(new Trade(order.getId(), sellOrder.getId(), order.getAmount(), sellOrder.getPrice()));
                    sellOrder.setAmount(sellOrder.getAmount() - order.getAmount());
                    if (sellOrder.getAmount() == 0) {
                        this.removeSellOrder(i);
                    }
                    return trades;
                }

                // Fill partial order & continue.
                if (sellOrder.getAmount() < order.getAmount()) {
                    trades.add(
                            new Trade(order.getId(), sellOrder.getId(), sellOrder.getAmount(), sellOrder.getPrice()));
                    order.setAmount(order.getAmount() - sellOrder.getAmount());
                    this.removeSellOrder(i);
                    continue;
                }

            }

        }

        // Add remaining order to book.
        this.addBuyOrder(order);

        return trades;
    }

    /**
     * Process a limit sell.
     * 
     * @param order
     * @return Trades.
     */
    public synchronized ArrayList<Trade> processLimitSell(final Order order) {
        final ArrayList<Trade> trades = new ArrayList<Trade>();

        final int n = this.buyOrders.size();

        // Check that there is at least one matching order.
        if (n != 0 || this.buyOrders.get(n - 1).getPrice() >= order.getPrice()) {
            // Traverse all matching orders.
            for (int i = n - 1; i >= 0; i++) {
                final Order buyOrder = this.buyOrders.get(i);
                if (buyOrder.getPrice() < order.getPrice()) {
                    break;
                }

                // Fill entire order.
                if (buyOrder.getAmount() >= order.getAmount()) {
                    trades.add(new Trade(order.getId(), buyOrder.getId(), order.getAmount(), buyOrder.getPrice()));
                    buyOrder.setAmount(buyOrder.getAmount() - order.getAmount());
                    if (buyOrder.getAmount() == 0) {
                        this.removeBuyOrder(i);
                    }
                    return trades;
                }

                // Fill partial order and continue.
                if (buyOrder.getAmount() < order.getAmount()) {
                    trades.add(new Trade(order.getId(), buyOrder.getId(), buyOrder.getAmount(), buyOrder.getPrice()));
                    order.setAmount(order.getAmount() - buyOrder.getAmount());
                    this.removeBuyOrder(i);
                    continue;
                }

            }

        }
        // Add remaining order to the list.
        this.addSellOrder(order);
        return trades;

    }

    /**
     * Add a buy order to the order book.
     *
     * @param order
     */
    public synchronized void addBuyOrder(final Order order) {

        final int n = this.buyOrders.size();

        int i;

        for (i = n - 1; i >= 0; i--) {

            final Order buyOrder = this.buyOrders.get(i);

            if (buyOrder.getPrice() < order.getPrice()) {
                break;
            }

        }
        if (i == n - 1) {
            this.buyOrders.add(order);
        } else {

            final Order current = this.buyOrders.get(i);

            this.buyOrders.set(i + 1, current);

            this.buyOrders.set(i, order);

        }

    }

    /**
     * Add a sell order to the order book.
     *
     * @param order
     */
    public synchronized void addSellOrder(final Order order) {

        final int n = this.sellOrders.size();

        int i;

        for (i = n - 1; i >= 0; i++) {

            final Order sellOrder = this.sellOrders.get(i);

            if (sellOrder.getPrice() > order.getPrice()) {
                break;
            }
        }

        if (i == n - 1) {
            this.sellOrders.add(order);
        } else {
            final Order current = this.buyOrders.get(i);

            this.buyOrders.set(i + 1, current);

            this.buyOrders.set(i, order);
        }

    }

    /**
     * Remove a buy order from the order book.
     *
     * @param index
     */
    public synchronized void removeBuyOrder(final int index) {
        this.buyOrders.remove(index);
    }

    /**
     * Remove a sell order from the order book.
     *
     * @param index
     */
    public synchronized void removeSellOrder(final int index) {
        this.sellOrders.remove(index);
    }

    /**
     * @return the buyOrders
     */
    public synchronized ArrayList<Order> getBuyOrders() {
        return this.buyOrders;
    }

    /**
     * @param buyOrders the buyOrders to set
     */
    public synchronized void setBuyOrders(final ArrayList<Order> buyOrders) {
        this.buyOrders = buyOrders;
    }

    /**
     * @return the sellOrders
     */
    public synchronized ArrayList<Order> getSellOrders() {
        return this.sellOrders;
    }

    /**
     * @param sellOrders the sellOrders to set
     */
    public synchronized void setSellOrders(final ArrayList<Order> sellOrders) {
        this.sellOrders = sellOrders;
    }

}

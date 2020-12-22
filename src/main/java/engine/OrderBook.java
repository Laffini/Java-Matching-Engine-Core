package engine;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Laffini
 *
 */
public class OrderBook {

    private ArrayList<Order> buyOrders;
    private ArrayList<Order> sellOrders;
    private double lastSalePrice;

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
    private synchronized ArrayList<Trade> processLimitBuy(final Order order) {
        final ArrayList<Trade> trades = new ArrayList<Trade>();

        final int n = this.sellOrders.size();

        // Check if order book is empty.

        if (n != 0) {
            // Check if at least one matching order.
            if (this.sellOrders.get(n - 1).getPrice() <= order.getPrice()) {

                // Traverse matching orders
                while (true) {
                    final Order sellOrder = this.sellOrders.get(0);
                    if (sellOrder.getPrice() > order.getPrice()) {
                        break;
                    }
                    // Fill entire order.
                    if (sellOrder.getAmount() >= order.getAmount()) {
                        trades.add(
                                new Trade(order.getId(), sellOrder.getId(), order.getAmount(), sellOrder.getPrice()));
                        sellOrder.setAmount(sellOrder.getAmount() - order.getAmount());
                        if (sellOrder.getAmount() == 0) {
                            this.removeSellOrder(0);
                        }
                        this.setLastSalePrice(sellOrder.getPrice());
                        return trades;
                    }

                    // Fill partial order & continue.
                    if (sellOrder.getAmount() < order.getAmount()) {
                        trades.add(new Trade(order.getId(), sellOrder.getId(), sellOrder.getAmount(),
                                sellOrder.getPrice()));
                        order.setAmount(order.getAmount() - sellOrder.getAmount());
                        this.removeSellOrder(0);
                        this.setLastSalePrice(sellOrder.getPrice());
                        continue;
                    }

                }

            }
        }

        // Add remaining order to book.
        this.buyOrders.add(order);

        Collections.sort(this.buyOrders);

        return trades;
    }

    /**
     * Process a limit sell.
     *
     * @param order
     * @return Trades.
     */
    private synchronized ArrayList<Trade> processLimitSell(final Order order) {

        final ArrayList<Trade> trades = new ArrayList<Trade>();

        final int n = this.buyOrders.size();

        // Check if order book is empty.
        double currentPrice;
        if (n == 0) {
            currentPrice = -1;
        } else {
            currentPrice = this.buyOrders.get(n - 1).getPrice();
        }

        // Check that there is at least one matching order.
        if (n != 0 || currentPrice >= order.getPrice()) {
            // Traverse all matching orders.
            for (int i = 0; i >= 0; i++) {
                final Order buyOrder = this.buyOrders.get(i);

                // Fill entire order.
                if (buyOrder.getAmount() >= order.getAmount()) {
                    trades.add(new Trade(order.getId(), buyOrder.getId(), order.getAmount(), buyOrder.getPrice()));
                    buyOrder.setAmount(buyOrder.getAmount() - order.getAmount());
                    if (buyOrder.getAmount() == 0) {
                        this.removeBuyOrder(i);
                    }
                    this.setLastSalePrice(buyOrder.getPrice());
                    return trades;
                }

                // Fill partial order and continue.
                if (buyOrder.getAmount() < order.getAmount()) {
                    trades.add(new Trade(order.getId(), buyOrder.getId(), buyOrder.getAmount(), buyOrder.getPrice()));
                    order.setAmount(order.getAmount() - buyOrder.getAmount());
                    this.removeBuyOrder(i);
                    this.setLastSalePrice(buyOrder.getPrice());
                    continue;
                }

            }

        }
        // Add remaining order to the list.
        this.sellOrders.add(order);

        Collections.sort(this.sellOrders);

        return trades;

    }

    /**
     * Cancel an order when the side is unknown.
     *
     * @param orderId
     * @return is order cancelled.
     * @throws InterruptedException
     */
    public boolean cancelOrder(final String orderId) throws InterruptedException {
        // Check if either cancelled.
        if (this.cancelBuyOrder(orderId) == true || this.cancelSellOrder(orderId)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Cancel an order when the side is known.
     *
     * @param orderId
     * @param side
     * @return is order cancelled.
     * @throws InterruptedException
     */
    public boolean cancelOrder(final String orderId, final Side side) throws InterruptedException {

        if (side == Side.BUY) {
            // Search buy orders.
            return this.cancelBuyOrder(orderId);

        } else if (side == Side.SELL) {
            // Search sell orders.
            return this.cancelSellOrder(orderId);

        } else {
            return false;
        }

    }

    /**
     * Cancel a buy order.
     *
     * @param orderId
     * @return
     * @throws InterruptedException
     */
    private boolean cancelBuyOrder(final String orderId) throws InterruptedException {
        final OrderCanceller buyOrders = new OrderCanceller(this.buyOrders, orderId);
        final Thread buyOrdersThread = new Thread(buyOrders);
        buyOrdersThread.start();
        buyOrdersThread.join();
        return buyOrders.isOrderCancelled();
    }

    /**
     * Cancel a sell order.
     *
     * @param orderId
     * @return
     * @throws InterruptedException
     */
    private boolean cancelSellOrder(final String orderId) throws InterruptedException {
        final OrderCanceller sellOrders = new OrderCanceller(this.sellOrders, orderId);
        final Thread sellOrdersThread = new Thread(sellOrders);
        sellOrdersThread.start();
        sellOrdersThread.join();
        return sellOrders.isOrderCancelled();
    }

    /**
     * Calculate the spread.
     *
     * @return Difference in buy and sell books.
     */
    public double getSpread() {

        if (this.buyOrders.size() != 0 && this.sellOrders.size() != 0) {
            final double buyOrderPrice = this.buyOrders.get(this.buyOrders.size() - 1).getPrice();

            final double sellOrderPrice = this.sellOrders.get(0).getPrice();

            return sellOrderPrice - buyOrderPrice;
        }

        return 0;

    }

    /**
     * Remove a buy order from the order book.
     *
     * @param index
     */
    private synchronized void removeBuyOrder(final int index) {
        this.buyOrders.remove(index);
    }

    /**
     * Remove a sell order from the order book.
     *
     * @param index
     */
    private synchronized void removeSellOrder(final int index) {
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

    /**
     * @return the lastSalePrice
     */
    public synchronized double getLastSalePrice() {
        return this.lastSalePrice;
    }

    /**
     * @param lastSalePrice the lastSalePrice to set
     */
    public synchronized void setLastSalePrice(final double lastSalePrice) {
        this.lastSalePrice = lastSalePrice;
    }

}

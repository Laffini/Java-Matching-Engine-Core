package net.laffyco.javamatchingengine.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * The order book.
 *
 * @author Laffini
 *
 */
@Component
public class OrderBook {

    /**
     * List of asking buy orders.
     */
    private List<Order> buyOrders;

    /**
     * List of asking sell orders.
     */
    private List<Order> sellOrders;

    /**
     * Last sale price.
     */
    private double lastSalePrice;

    /**
     * Create an instance of OrderBook.
     *
     * @param pBuyOrders
     * @param pSellOrders
     */
    public OrderBook(final List<Order> pBuyOrders,
            final List<Order> pSellOrders) {
        super();
        this.buyOrders = pBuyOrders;
        this.sellOrders = pSellOrders;
    }

    /**
     * Process an order and return the trades generated before adding the
     * remaining amount to the market.
     *
     * @param pOrder
     * @return trades
     */
    public synchronized ArrayList<Trade> process(final Order pOrder) {

        if (pOrder.getSide() == Side.BUY) {
            return this.processLimitBuy(pOrder);
        } else {
            return this.processLimitSell(pOrder);
        }

    }

    /**
     * Process limit buy.
     *
     * @param order
     * @return trades
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
                        trades.add(new Trade(order.getId(), sellOrder.getId(),
                                order.getAmount(), sellOrder.getPrice()));
                        sellOrder.setAmount(
                                sellOrder.getAmount() - order.getAmount());
                        if (sellOrder.getAmount() == 0) {
                            this.removeSellOrder(0);
                        }
                        this.setLastSalePrice(sellOrder.getPrice());
                        return trades;
                    }

                    // Fill partial order & continue.
                    if (sellOrder.getAmount() < order.getAmount()) {
                        trades.add(new Trade(order.getId(), sellOrder.getId(),
                                sellOrder.getAmount(), sellOrder.getPrice()));
                        order.setAmount(
                                order.getAmount() - sellOrder.getAmount());
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
                    trades.add(new Trade(order.getId(), buyOrder.getId(),
                            order.getAmount(), buyOrder.getPrice()));
                    buyOrder.setAmount(
                            buyOrder.getAmount() - order.getAmount());
                    if (buyOrder.getAmount() == 0) {
                        this.removeBuyOrder(i);
                    }
                    this.setLastSalePrice(buyOrder.getPrice());
                    return trades;
                }

                // Fill partial order and continue.
                if (buyOrder.getAmount() < order.getAmount()) {
                    trades.add(new Trade(order.getId(), buyOrder.getId(),
                            buyOrder.getAmount(), buyOrder.getPrice()));
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
     * Calculate the spread.
     *
     * @return Difference in buy and sell books.
     */
    public double getSpread() {

        if (this.buyOrders.size() != 0 && this.sellOrders.size() != 0) {
            final double buyOrderPrice = this.buyOrders
                    .get(this.buyOrders.size() - 1).getPrice();

            final double sellOrderPrice = this.sellOrders.get(0).getPrice();

            return sellOrderPrice - buyOrderPrice;
        }
        return 0;
    }

    /**
     * Cancel an order when the side is known.
     *
     * @param orderId
     * @param side
     * @return is order cancelled.
     */
    public synchronized boolean cancelOrder(final String orderId,
            final Side side) {
        if (side == Side.BUY) {
            // Search buy orders.
            return this.cancel(orderId, this.buyOrders);
        } else if (side == Side.SELL) {
            // Search sell orders.
            return this.cancel(orderId, this.sellOrders);
        } else {
            return false;
        }

    }

    /**
     * Cancel an order from an order book.
     *
     * @param orderId
     * @param orderBook
     * @return whether an order has been cancelled
     */
    private synchronized boolean cancel(final String orderId,
            final List<Order> orderBook) {
        // Loop through order book to find order.
        for (int i = 0; i < orderBook.size(); i++) {
            final Order currentOrder = orderBook.get(i);
            if (currentOrder.getId().equals(orderId)) {
                orderBook.remove(i);
                return true; // Order cancelled.
            }
        }
        return false; // No order cancelled.
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
    public synchronized List<Order> getBuyOrders() {
        return this.buyOrders;
    }

    /**
     * @param pBuyOrders the buyOrders to set
     */
    public synchronized void setBuyOrders(final ArrayList<Order> pBuyOrders) {
        this.buyOrders = pBuyOrders;
    }

    /**
     * @return the sellOrders
     */
    public synchronized List<Order> getSellOrders() {
        return this.sellOrders;
    }

    /**
     * @param pSellOrders the sellOrders to set
     */
    public synchronized void setSellOrders(final ArrayList<Order> pSellOrders) {
        this.sellOrders = pSellOrders;
    }

    /**
     * @return the lastSalePrice
     */
    public synchronized double getLastSalePrice() {
        return this.lastSalePrice;
    }

    /**
     * @param pLastSalePrice the lastSalePrice to set
     */
    public synchronized void setLastSalePrice(final double pLastSalePrice) {
        this.lastSalePrice = pLastSalePrice;
    }
}

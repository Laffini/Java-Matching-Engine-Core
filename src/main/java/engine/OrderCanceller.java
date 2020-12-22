package engine;

import java.util.ArrayList;

/**
 * Remove an order in a new thread.
 * 
 * @author Laffini
 *
 */
public class OrderCanceller implements Runnable {

    private final ArrayList<Order> book;
    private final String orderId;
    private boolean orderCancelled = false;

    /**
     * Create a new instance of the Order Canceller.
     * 
     * @param book
     * @param orderId
     */
    public OrderCanceller(final ArrayList<Order> book, final String orderId) {
        this.book = book;
        this.orderId = orderId;
    }

    @Override
    public void run() {

        // Loop through order book to find order.
        for (int i = 0; i < this.book.size(); i++) {

            final Order currentOrder = this.book.get(i);

            if (currentOrder.getId().equals(this.orderId)) {

                this.book.remove(i);
                this.orderCancelled = true;

            }

        }

    }

    /**
     * @return the orderCancelled
     */
    public synchronized boolean isOrderCancelled() {
        return this.orderCancelled;
    }

}

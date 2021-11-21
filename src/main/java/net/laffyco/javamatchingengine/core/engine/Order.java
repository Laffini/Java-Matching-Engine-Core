package net.laffyco.javamatchingengine.core.engine;

import java.util.Date;
import java.util.UUID;

/**
 * Orders.
 *
 * @author Laffini
 *
 */
public class Order implements Comparable<Order> {

    /**
     * Size of the order.
     */
    private double amount;

    /**
     * Order price.
     */
    private double price;

    /**
     * ID.
     */
    private String id;

    /**
     * Side of the order.
     */
    private Side side;

    /**
     * Date & Time of order.
     */
    private final Date dateTimeOfOrder;

    /**
     * Create an instance of Order. Date & time set to now.
     *
     * @param pAmount
     * @param pPrice
     * @param pSide
     */
    public Order(final double pAmount, final double pPrice, final Side pSide) {
        this(pAmount, pPrice, UUID.randomUUID().toString(), pSide, new Date());
    }

    /**
     * Create an instance of Order.
     *
     * @param pAmount
     * @param pPrice
     * @param pId
     * @param pSide
     * @param pDateTimeOfOrder
     */
    public Order(final double pAmount, final double pPrice, final String pId,
            final Side pSide, final Date pDateTimeOfOrder) {

        this.amount = pAmount;
        this.price = pPrice;
        this.id = pId;
        this.side = pSide;
        this.dateTimeOfOrder = pDateTimeOfOrder;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return this.amount;
    }

    /**
     * @param pAmount the amount to set
     */
    public void setAmount(final double pAmount) {
        this.amount = pAmount;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * @param pPrice the price to set
     */
    public void setPrice(final double pPrice) {
        this.price = pPrice;
    }

    /**
     * @return the id
     */
    public String getId() {
        return this.id;
    }

    /**
     * @param pId the id to set
     */
    public void setId(final String pId) {
        this.id = pId;
    }

    /**
     * @return the side
     */
    public Side getSide() {
        return this.side;
    }

    /**
     * @param pSide the side to set
     */
    public void setSide(final Side pSide) {
        this.side = pSide;
    }

    /**
     * @return the dateTimeOfOrder
     */
    public Date getDateTimeOfOrder() {
        return this.dateTimeOfOrder;
    }

    /**
     * Compare two orders by price & time.
     *
     * @param o
     */
    @Override
    public int compareTo(final Order o) {

        if (Double.compare(this.getPrice(), o.getPrice()) == 0) {
            // If equal, check date & time.
            if (this.getDateTimeOfOrder().before(o.getDateTimeOfOrder())) {
                // The current Order occurred before, therefore should appear
                // first.
                return -1;
            } else {
                return 1;
            }

        } else {
            return Double.compare(this.getPrice(), o.getPrice());
        }
    }

}

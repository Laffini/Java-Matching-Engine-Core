package net.laffyco.javamatchingengine.core.engine;

import java.util.Date;
import java.util.Optional;
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
     * Class for building orders.
     *
     * @author Laffini
     *
     */
    public static class Builder {

        /**
         * Size of the order.
         */
        private double amount;

        /**
         * Order price.
         */
        private double price;

        /**
         * Side of the order.
         */
        private final Side side;

        /**
         * Date & Time of order.
         */
        private Date dateTimeOfOrder;

        /**
         * ID.
         */
        private String id;

        /**
         * Builder constructor.
         *
         * @param pSide
         */
        public Builder(final Side pSide) {
            this.side = pSide;
        }

        /**
         * The order's amount.
         *
         * @param amt
         * @return The builder.
         */
        public Builder withAmount(final double amt) {
            this.amount = amt;
            return this;
        }

        /**
         * The order's price.
         *
         * @param pPrice
         * @return The builder.
         */
        public Builder atPrice(final double pPrice) {
            this.price = pPrice;
            return this;
        }

        /**
         * The order's ID.
         *
         * @param pId
         * @return The builder.
         */
        public Builder withId(final String pId) {
            this.id = pId;
            return this;
        }

        /**
         * The order's date time stamp.
         *
         * @param dateTime
         * @return The builder.
         */
        public Builder withDateTime(final Date dateTime) {
            this.dateTimeOfOrder = dateTime;
            return this;
        }

        /**
         * Build an order.
         *
         * @return The new order.
         */
        public Order build() {
            return new Order(this.amount, this.price,
                    Optional.ofNullable(this.id), this.side,
                    Optional.ofNullable(this.dateTimeOfOrder));
        }

    }

    /**
     * Create an instance of Order. Private so that the builder is used.
     *
     * @param pAmount
     * @param pPrice
     * @param pId
     * @param pSide
     * @param pDateTimeOfOrder
     */
    private Order(final double pAmount, final double pPrice,
            final Optional<String> pId, final Side pSide,
            final Optional<Date> pDateTimeOfOrder) {

        if (pPrice <= 0) {
            throw new IllegalArgumentException(
                    "Order prices must be greater than zero");
        } else if (pAmount <= 0) {
            throw new IllegalArgumentException(
                    "Order amounts must be greater than zero");
        }

        this.amount = pAmount;
        this.price = pPrice;
        this.id = pId.orElse(UUID.randomUUID().toString());
        this.side = pSide;
        this.dateTimeOfOrder = pDateTimeOfOrder.orElse(new Date());
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

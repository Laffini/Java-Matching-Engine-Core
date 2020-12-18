package engine;

import java.util.Date;

/**
 * @author Laffini
 *
 */
public class Order implements Comparable<Order> {

    private double amount;
    private double price;
    private String id;
    private Side side;
    private final Date dateTimeOfOrder;

    /**
     * Create an instance of Order.
     *
     * @param amount
     * @param price
     * @param id
     * @param side
     */
    public Order(final int amount, final double price, final String id, final Side side, final Date dateTimeOfOrder) {
        super();
        this.amount = amount;
        this.price = price;
        this.id = id;
        this.side = side;
        this.dateTimeOfOrder = dateTimeOfOrder;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return this.amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(final double amount) {
        this.amount = amount;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(final double price) {
        this.price = price;
    }

    /**
     * @return the id
     */
    public String getId() {
        return this.id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * @return the side
     */
    public Side getSide() {
        return this.side;
    }

    /**
     * @param side the side to set
     */
    public void setSide(final Side side) {
        this.side = side;
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
                // The current Order occurred before, therefore should appear first.
                return -1;
            } else {
                return 1;
            }

        } else {
            return Double.compare(this.getPrice(), o.getPrice());
        }
    }

}
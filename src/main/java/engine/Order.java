package engine;

/**
 * @author Laffini
 *
 */
public class Order implements Comparable<Order> {

    private double amount;
    private double price;
    private String id;
    private Side side;

    /**
     * Create an instance of Order.
     *
     * @param amount
     * @param price
     * @param id
     * @param side
     */
    public Order(final int amount, final double price, final String id, final Side side) {
        super();
        this.amount = amount;
        this.price = price;
        this.id = id;
        this.side = side;
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
     * Compare two orders by price.
     * 
     * @param o
     */
    public int compareTo(final Order o) {
        return Double.compare(this.getPrice(), o.getPrice());
    }

}
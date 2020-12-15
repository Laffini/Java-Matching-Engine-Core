package engine;

/**
 * @author Laffini
 *
 */
public class Order {

    private int orderId;
    private String instrument;
    private double price;
    private double quantity;
    private double cumulativeQuantity;
    private double leavesQuantity;
    private Side side;

    /**
     * Create a new instance of Order
     *
     * @param orderId
     * @param instrument
     * @param price
     * @param quantity
     * @param cumulativeQuantity
     * @param leavesQuantity
     * @param side
     */
    public Order(final int orderId, final String instrument, final double price, final double quantity,
            final double cumulativeQuantity, final double leavesQuantity, final Side side) {
        this.orderId = orderId;
        this.instrument = instrument;
        this.price = price;
        this.quantity = quantity;
        this.cumulativeQuantity = cumulativeQuantity;
        this.leavesQuantity = leavesQuantity;
        this.side = side;
    }

    /**
     * @return the orderId
     */
    public int getOrderId() {
        return this.orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(final int orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the instrument
     */
    public String getInstrument() {
        return this.instrument;
    }

    /**
     * @param instrument the instrument to set
     */
    public void setInstrument(final String instrument) {
        this.instrument = instrument;
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
     * @return the quantity
     */
    public double getQuantity() {
        return this.quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(final double quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the cumulativeQuantity
     */
    public double getCumulativeQuantity() {
        return this.cumulativeQuantity;
    }

    /**
     * @param cumulativeQuantity the cumulativeQuantity to set
     */
    public void setCumulativeQuantity(final double cumulativeQuantity) {
        this.cumulativeQuantity = cumulativeQuantity;
    }

    /**
     * @return the leavesQuantity
     */
    public double getLeavesQuantity() {
        return this.leavesQuantity;
    }

    /**
     * @param leavesQuantity the leavesQuantity to set
     */
    public void setLeavesQuantity(final double leavesQuantity) {
        this.leavesQuantity = leavesQuantity;
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

}

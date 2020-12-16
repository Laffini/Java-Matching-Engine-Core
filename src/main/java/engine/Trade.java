package engine;

/**
 * @author Laffini
 *
 */
public class Trade {

    private String takerOrderId;
    private String makerOrderId;
    private double amount;
    private double price;

    /**
     * Create an instance of Trade.
     *
     * @param takerOrderId
     * @param makerOrderId
     * @param amount
     * @param price
     */
    public Trade(final String takerOrderId, final String makerOrderId, final double amount, final double price) {
        super();
        this.takerOrderId = takerOrderId;
        this.makerOrderId = makerOrderId;
        this.amount = amount;
        this.price = price;
    }

    /**
     * @return the takerOrderId
     */
    public String getTakerOrderId() {
        return this.takerOrderId;
    }

    /**
     * @param takerOrderId the takerOrderId to set
     */
    public void setTakerOrderId(final String takerOrderId) {
        this.takerOrderId = takerOrderId;
    }

    /**
     * @return the makerOrderId
     */
    public String getMakerOrderId() {
        return this.makerOrderId;
    }

    /**
     * @param makerOrderId the makerOrderId to set
     */
    public void setMakerOrderId(final String makerOrderId) {
        this.makerOrderId = makerOrderId;
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

}

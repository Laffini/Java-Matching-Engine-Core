package net.laffyco.javamatchingengine.scenarios.orders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.laffyco.javamatchingengine.core.engine.Order;
import net.laffyco.javamatchingengine.core.engine.OrderBook;
import net.laffyco.javamatchingengine.core.engine.Side;

/**
 * Orders feature steps.
 *
 * @author Laffini
 */
public class OrdersSteps {

    /**
     * Order book instance.
     */
    private OrderBook orderBook;

    /**
     * Order to add to order book.
     */
    private Order order;

    /**
     * Create the order book.
     */
    @Given("There is an instance of the order book")
    public void createOrderBook() {
        this.orderBook = new OrderBook();
    }

    /**
     * Add the order.
     */
    @When("I add an order")
    public void addOrder() {
        final double amount = 10;
        final double price = 25.59;
        final Side side = Side.BUY;

        this.order = new Order.Builder(side).withAmount(amount).atPrice(price)
                .build();
        this.orderBook.process(this.order);
    }

    /**
     * Confirm order is added.
     */
    @Then("It is added to the order book")
    public void orderIsAdded() {
        final List<Order> buyOrders = this.orderBook.getBuyOrders();

        // Assert there is only one order.
        final int oneOrder = 1;
        assertEquals(oneOrder, buyOrders.size());
        assertTrue(this.orderBook.getSellOrders().isEmpty());

        // Assert orders are equal match.
        final Order addedOrder = buyOrders.get(0);
        assertEquals(this.order, addedOrder);
    }
}

package net.laffyco.javamatchingengine.core.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.annotation.Resource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

/**
 * Test the getSpread() functionality.
 *
 * @author Laffini
 */
public class SpreadTests extends MatchingEngineTest {

    /**
     * Order book.
     */
    @InjectMocks
    @Resource
    private OrderBook orderBook;

    @Test
    @DisplayName("Get the spread when there are two orders")
    void spreadTwoOrders() {
        final Order[] orders = {
                new Order.Builder(Side.SELL).withAmount(2).atPrice(2.5)
                        .withId("sellOrder").build(),
                new Order.Builder(Side.BUY).withAmount(2).atPrice(2)
                        .withId("buyOrder").build() };
        this.addOrders(this.orderBook, orders);

        final double expectedSpread = 0.5;

        assertEquals(expectedSpread, this.orderBook.getSpread());
    }

    @Test
    @DisplayName("Get the spread when there are multiple orders")
    void spreadMultipleOrders() {
        final Order[] orders = {
                new Order.Builder(Side.SELL).withAmount(2).atPrice(2.5)
                        .withId("sellOrder").build(),
                new Order.Builder(Side.SELL).withAmount(2).atPrice(2.75)
                        .withId("secondSellOrder").build(),
                new Order.Builder(Side.BUY).withAmount(2).atPrice(2)
                        .withId("buyOrder").build(),
                new Order.Builder(Side.BUY).withAmount(2).atPrice(1.5)
                        .withId("secondBuyOrder").build() };
        this.addOrders(this.orderBook, orders);

        final double expectedSpread = 0.5;

        assertEquals(expectedSpread, this.orderBook.getSpread());
    }

    @Test
    @DisplayName("Attempt to get the spread when there are no orders")
    void spreadNoOrders() {
        assertEquals(this.orderBook.getSpread(), 0);
    }

}

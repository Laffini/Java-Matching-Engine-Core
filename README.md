# Java-Matching-Engine

**In Progress** 

![Java CI with Maven](https://github.com/Laffini/Java-Matching-Engine/workflows/Java%20CI%20with%20Maven/badge.svg)

A matching engine written in Java.

## What is a matching engine?
A matching engine matches buy and sell orders in a market.

## Matching Algorithm
The matching engine uses a price-time-priority algorithm. The matching priority is firstly price, then time. Market participants are rewarded for offering the best price and coming early. 

## Usage Examples
```Java
    public static void main(final String[] args) { 

        // Create the order book.
        final OrderBook book = new OrderBook(new ArrayList<Order>(), new ArrayList<Order>());
        
        // Create the sell order. (amount, price, id, side, date of order).
        final Order sellOrder = new Order(2, 2.50, "myFirstSellOrder", Side.SELL, new Date());
        
        // Create the buy order. (amount, price, id, side, date of order).
        final Order buyOrder = new Order(2, 2.50, "myFirstBuyOrder", Side.BUY, new Date());

        // Process the orders.
        book.process(sellOrder);
        // Processing an order returns the trades made. In this case we know that the order will be filled.
        final ArrayList<Trade> trades = book.process(buyOrder); 

    }
``` 

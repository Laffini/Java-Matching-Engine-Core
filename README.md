# Java-Matching-Engine

![Java CI with Maven](https://github.com/Laffini/Java-Matching-Engine/workflows/Java%20CI%20with%20Maven/badge.svg)

A matching engine written in Java.

## What is a matching engine?
A matching engine matches buy and sell orders in a market.

## Matching Algorithm
The matching engine uses a price-time-priority algorithm.
 The matching priority is firstly price, then time.
 Market participants are rewarded for offering the best price and coming early. 

## Usage
The Java-Matching-Engine is broken up into multiple projects.
This is the 'core' project.
 This project only contains the engine (no REST API, persistence, etc).

To use this project, you will need to include the 'core' package as part of your Spring configuration scanning (as seen below).
```
@SpringBootApplication(scanBasePackages = "net.laffyco.javamatchingengine.core")
public class ExampleApp {

	@Autowired
    private OrderInterface orderInterface

    public static void main(final String[] args) {
        SpringApplication.run(DbApp.class, args);
        System.out.println(this.getOrders());
    }

}

```

In the example above, we are interacting with the order book through the OrderInterface. 
The example starts and prints the orders to the console (it will be empty as no orders have been added).
In most cases you will want to use some of the other Java-Matching-Engine projects as these add additional functionality (such as a REST API) that may suit your needs.

It is recommended that every user of this service audits and verifies all underlying code for its validity and suitability. Mistakes and bugs happen.
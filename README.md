# Java-Matching-Engine

**This service is currently being rewritten. Not all functionality has been implemented yet.**

![Java CI with Maven](https://github.com/Laffini/Java-Matching-Engine/workflows/Java%20CI%20with%20Maven/badge.svg)

A matching engine written in Java.

## What is a matching engine?
A matching engine matches buy and sell orders in a market.

## Matching Algorithm
The matching engine uses a price-time-priority algorithm. The matching priority is firstly price, then time. Market participants are rewarded for offering the best price and coming early. 

## Usage
To use this API, you will need to run a local service which will be responsible for managing your matching engine. Your application will interact with this service locally via HTTP API calls. By default the service runs on the port 3000.

It is recommended that every user of this service audits and verifies all underlying code for its validity and suitability. Mistakes and bugs happen.

## Usage Examples
### Buying and Selling Example
#### Creating a maker order
User A creates a buy order of 2 items (e.g. 2 shares, 2 bitcoin, etc) with a price of 5 (e.g. £5, $5, etc)
##### Request
```
POST /order/
	?side=BUY
    &amount=2
    &price=5
``` 
##### Response
```
{
  "trades": [],
  "id": "02923822-070b-448d-9240-f3623f90927a"
}
```
A unique ID is generated for the order (so that it can be modified/viewed). A list of trades is also returned. In this example the list of trades returned is empty as no trades have been made (due to the order book being empty).
#### Creating a taker order
User B creates a sell order of 2 items with a price of 5.
##### Request
```
POST /order/
	?side=SELL
    &amount=2
    &price=5
``` 
##### Response
```
{
  "trades": [
    {
      "takerOrderId": "12964731-2cd4-401b-ac8d-3853f58f75c0",
      "makerOrderId": "02923822-070b-448d-9240-f3623f90927a",
      "amount": 2,
      "price": 5
    }
  ],
  "id": "12964731-2cd4-401b-ac8d-3853f58f75c0"
}
```
As the sell order matches the buy order created earlier we can see that a trade has taken place.
# Java-Matching-Engine

** This service is currently being rewritten. Not all functionality has been implemented yet. **

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
```Java
//TODO
``` 

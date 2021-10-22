Feature: Orders

  Tests related to orders.

  Scenario: Adding an order
    Given There is an instance of the order book
    When I add an order
    Then It is added to the order book

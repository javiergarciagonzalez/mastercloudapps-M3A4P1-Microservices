package io.eventuate.examples.tram.sagas.ordersandcustomers.orders.webapi;


import io.eventuate.examples.tram.sagas.ordersandcustomers.commondomain.Money;
import io.eventuate.examples.tram.sagas.ordersandcustomers.commondomain.Stock;

public class CreateOrderRequest {
  private Money orderTotal;
  private Long customerId;
  private Long productId;
  private Stock stock;

  public CreateOrderRequest() {
  }

  public CreateOrderRequest(Long customerId, Money orderTotal, Long productId, Stock stock) {
    this.customerId = customerId;
    this.orderTotal = orderTotal;
    this.productId = productId;
    this.stock = stock;
  }

  public Money getOrderTotal() {
    return orderTotal;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public Long getProductId() {
    return productId;
  }

  public Stock getStock() {
    return stock;
  }
}

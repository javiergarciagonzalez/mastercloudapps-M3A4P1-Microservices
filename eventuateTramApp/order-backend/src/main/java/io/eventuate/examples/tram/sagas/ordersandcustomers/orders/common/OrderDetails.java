package io.eventuate.examples.tram.sagas.ordersandcustomers.orders.common;

import io.eventuate.examples.tram.sagas.ordersandcustomers.commondomain.Money;
import io.eventuate.examples.tram.sagas.ordersandcustomers.commondomain.Stock;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class OrderDetails {

  private Long customerId;

  private Long productId;
  
  @Embedded
  private Stock stock;

  @Embedded
  private Money orderTotal;

  public OrderDetails() {
  }

  public OrderDetails(Long customerId, Money orderTotal, Long productId, Stock stock) {
    this.customerId = customerId;
    this.orderTotal = orderTotal;
    this.productId = productId;
    this.stock = stock;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public Money getOrderTotal() {
    return orderTotal;
  }

    public Long getProductId() {
    return productId;
  }

  public Stock getStock() {
    return stock;
  }
}

package io.eventuate.examples.tram.sagas.ordersandcustomers.customers.apigateway;

import io.eventuate.examples.tram.sagas.ordersandcustomers.commondomain.Money;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.webapi.GetOrderResponse;
// import io.eventuate.examples.tram.sagas.ordersandcustomers.products.webapi.GetProductResponse;

import java.util.List;

public class GetCustomerHistoryResponse {
  private Long customerId;
  private String name;
  private Money creditLimit;
  private List<GetOrderResponse> orders;
  // private List<GetProductResponse> products;

  public GetCustomerHistoryResponse() {
  }

  public GetCustomerHistoryResponse(Long customerId, String name, Money creditLimit, List<GetOrderResponse> orders) {
    this.customerId = customerId;
    this.name = name;
    this.creditLimit = creditLimit;
    this.orders = orders;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Money getCreditLimit() {
    return creditLimit;
  }

  public void setCreditLimit(Money creditLimit) {
    this.creditLimit = creditLimit;
  }

  public List<GetOrderResponse> getOrders() {
    return orders;
  }

  public void setOrders(List<GetOrderResponse> orders) {
    this.orders = orders;
  }

  // public List<GetProductResponse> getProducts() {
  //   return products;
  // }
}

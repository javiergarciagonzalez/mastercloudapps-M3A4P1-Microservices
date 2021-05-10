package io.eventuate.examples.tram.sagas.ordersandcustomers.products.webapi;

import io.eventuate.examples.tram.sagas.ordersandcustomers.commondomain.Stock;

public class CreateProductRequest {
  private String name;
  private String description;
  private Stock stock;

  public CreateProductRequest() {
  }

  public CreateProductRequest(String name, Stock stock, String description) {
    this.name = name;
    this.stock = stock;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Stock getStock() {
    return stock;
  }
}

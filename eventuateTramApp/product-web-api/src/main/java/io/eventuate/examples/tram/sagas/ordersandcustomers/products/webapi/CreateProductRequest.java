package io.eventuate.examples.tram.sagas.ordersandcustomers.products.webapi;

public class CreateProductRequest {
  private String name;
  private String description;
  private int stock;

  public CreateProductRequest() {
  }

  public CreateProductRequest(String name, int stock, String description) {
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

  public int getStock() {
    return stock;
  }
}

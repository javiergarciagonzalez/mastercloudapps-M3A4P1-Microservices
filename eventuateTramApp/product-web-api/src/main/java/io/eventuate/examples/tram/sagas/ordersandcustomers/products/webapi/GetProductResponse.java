package io.eventuate.examples.tram.sagas.ordersandcustomers.products.webapi;

public class GetProductResponse {
  private Long productId;
  private String name;
  private String description;
  private int stock;

  public GetProductResponse() {
  }

  public GetProductResponse(Long productId, String name, int stock, String description) {
    this.productId = productId;
    this.name = name;
    this.stock = stock;
    this.description = description;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }
}

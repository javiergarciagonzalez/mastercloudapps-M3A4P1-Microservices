package io.eventuate.examples.tram.sagas.ordersandcustomers.products.api.commands;

import io.eventuate.examples.tram.sagas.ordersandcustomers.commondomain.Stock;
import io.eventuate.tram.commands.common.Command;

public class ReserveStockCommand implements Command {
  private Long orderId;
  private Stock stock;
  private long productId;

  public ReserveStockCommand() {
  }

  public ReserveStockCommand(Long productId, Long orderId, Stock stock) {
    this.productId = productId;
    this.orderId = orderId;
    this.stock = stock;
  }

  public Stock getStock() {
    return stock;
  }

  public void setStock(Stock stock) {
    this.stock = stock;
  }

  public Long getOrderId() {

    return orderId;
  }

  public void setOrderId(Long orderId) {

    this.orderId = orderId;
  }

  public long getProductId() {
    return productId;
  }

  public void setProductId(long productId) {
    this.productId = productId;
  }
}
package io.eventuate.examples.tram.sagas.ordersandcustomers.commondomain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class Stock {

  public static final Stock ZERO = new Stock(0);
  private int stock;

  public Stock() {
  }

  public Stock(int stock) {
    this.stock = stock;
  }

  public Stock(String stock) {
    this.stock = new Integer(stock);
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }

  public Stock add(Stock stock) {
    int amount = stock.getStock();
    this.stock += amount;
    return this;
  }
  public Stock subtract(Stock stock) {
    int amount = stock.getStock();
    this.stock -= amount;
    return this;
  }
} 

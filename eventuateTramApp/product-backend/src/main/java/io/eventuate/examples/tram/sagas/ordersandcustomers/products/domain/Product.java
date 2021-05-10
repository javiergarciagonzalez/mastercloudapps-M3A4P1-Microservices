package io.eventuate.examples.tram.sagas.ordersandcustomers.products.domain;

import javax.persistence.*;
import java.util.Map;

import io.eventuate.examples.tram.sagas.ordersandcustomers.commondomain.Stock;

@Entity
@Table(name="products")
@Access(AccessType.FIELD)
public class Product {

  @Id
  @Column(nullable=false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String description;

  @Embedded
  private String name;

  @Embedded
  private Stock stock;

  @Version
  private Long version;

  @ElementCollection
  private Map<Long, Stock> bookedStock;

  public Product() {
  }

  public Product(String name, Stock stock, String description ) {
    this.name = name;
    this.stock = stock;
    this.description = description;
  }

  public Long getId() {
    return id;
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

  public void updateStock(Long orderId, Stock stock) {
    if (this.stock.getStock() - stock.getStock()< 0) {
      throw new ProductStockLimitExceededException();
    }
    this.bookedStock.put(orderId, stock);
  }

  public Stock availableStock() {
    return this.stock.subtract(this.bookedStock.values().stream().reduce(Stock.ZERO, Stock::add));
  }
}

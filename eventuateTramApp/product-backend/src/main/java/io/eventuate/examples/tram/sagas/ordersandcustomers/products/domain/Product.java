package io.eventuate.examples.tram.sagas.ordersandcustomers.products.domain;


import javax.persistence.*;
import java.util.Collections;
import java.util.Map;

@Entity
@Table(name="Product")
@Access(AccessType.FIELD)
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private int stock;
  private String description;

  @Embedded
  private int stock;

  @Embedded
  private String description;

  @Version
  private Long version;

  int getStock() {
    return stock;
  }

  public Product() {
  }

  public Product(String name, int stock, String description ) {
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

  public int getStock() {
    return stock;
  }

  public void updateStock(int stock) {
    this.stock = stock;
  }
}

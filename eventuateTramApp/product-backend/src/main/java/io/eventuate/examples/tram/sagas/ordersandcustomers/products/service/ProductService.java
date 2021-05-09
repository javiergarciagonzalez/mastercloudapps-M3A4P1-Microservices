package io.eventuate.examples.tram.sagas.ordersandcustomers.products.service;

import io.eventuate.examples.tram.sagas.ordersandcustomers.commondomain.Money;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.api.commands.ReserveCreditCommand;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.api.replies.ProductCreditLimitExceeded;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.api.replies.ProductCreditReserved;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.api.replies.ProductNotFound;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.domain.Product;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.domain.ProductCreditLimitExceededException;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.domain.ProductNotFoundException;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.domain.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

public class ProductService {

  private ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Transactional
  public Product createProduct(String name, String creditLimit) {
    Product product  = new Product(name, creditLimit);
    return productRepository.save(product);
  }

  public void reserveCredit(long productId, long orderId, Money orderTotal) throws ProductCreditLimitExceededException {
    Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
    product.reserveCredit(orderId, orderTotal);
  }
}

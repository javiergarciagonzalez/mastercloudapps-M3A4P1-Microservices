package io.eventuate.examples.tram.sagas.ordersandcustomers.integrationtests;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import io.eventuate.examples.tram.sagas.ordersandcustomers.commondomain.Money;
import io.eventuate.examples.tram.sagas.ordersandcustomers.commondomain.Stock;
import io.eventuate.examples.tram.sagas.ordersandcustomers.customers.domain.Customer;
import io.eventuate.examples.tram.sagas.ordersandcustomers.customers.service.CustomerService;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.common.OrderDetails;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.common.OrderState;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.domain.Order;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.domain.OrderRepository;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.service.OrderService;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.domain.Product;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.service.ProductService;

public abstract class AbstractOrdersAndCustomersIntegrationTest {

  @Autowired
  private CustomerService customerService;

  @Autowired
  private OrderService orderService;
  
  @Autowired
  private ProductService productService;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private TransactionTemplate transactionTemplate;

  final String PRODUCT_NAME = "Laptop";
  final String PRODUCT_DESCRIPTION = "A very porwelful machine.";

  @Test
  public void shouldApproveOrder() throws InterruptedException {
    Money creditLimit = new Money("15.00");
    Customer customer = customerService.createCustomer("Fred", creditLimit);
    Stock stock = new Stock(20);
    Product product = productService.createProduct(PRODUCT_NAME, stock, PRODUCT_DESCRIPTION);
    Order order = orderService.createOrder(new OrderDetails(customer.getId(), new Money("12.34"), product.getId(), stock));

    assertOrderState(order.getId(), OrderState.APPROVED);
  }

  @Test
  public void shouldRejectOrder() throws InterruptedException {
    Money creditLimit = new Money("15.00");
    Customer customer = customerService.createCustomer("Fred", creditLimit);
    Stock stock = new Stock(20);
    Product product = productService.createProduct(PRODUCT_NAME, stock, PRODUCT_DESCRIPTION);
    Order order = orderService.createOrder(new OrderDetails(customer.getId(), new Money("123.40"), product.getId(), stock));

    assertOrderState(order.getId(), OrderState.REJECTED);
  }

  private void assertOrderState(Long id, OrderState expectedState) throws InterruptedException {
    Order order = null;
    for (int i = 0; i < 30; i++) {
      order = transactionTemplate
              .execute(s -> orderRepository.findById(id))
              .orElseThrow(() -> new IllegalArgumentException(String.format("Order with id %s is not found", id)));
      if (order.getState() == expectedState)
        break;
      TimeUnit.MILLISECONDS.sleep(400);
    }

    assertEquals(expectedState, order.getState());
  }
}

package io.eventuate.examples.tram.sagas.ordersandcustomers.endtoendtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import io.eventuate.examples.tram.sagas.ordersandcustomers.commondomain.Money;
import io.eventuate.examples.tram.sagas.ordersandcustomers.commondomain.Stock;
import io.eventuate.examples.tram.sagas.ordersandcustomers.customers.webapi.CreateCustomerRequest;
import io.eventuate.examples.tram.sagas.ordersandcustomers.customers.webapi.CreateCustomerResponse;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.common.OrderState;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.common.RejectionReason;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.webapi.CreateOrderRequest;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.webapi.CreateOrderResponse;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.webapi.GetOrderResponse;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.webapi.CreateProductRequest;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.webapi.CreateProductResponse;
import io.eventuate.util.test.async.Eventually;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CustomersAndOrdersE2ETestConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AssignmentTests {

    @Value("${host.name}")
    private String hostName;

    private String CUSTOMER_NAME = "Javier";
    private String PRODUCT_NAME = "Laptop";
    private String PRODUCT_DESCRIPTION = "A very powerful machine.";

    private String baseUrl(String path, String...pathElements) {
        assertNotNull("host", hostName);

        StringBuilder sb = new StringBuilder("http://");
        sb.append(hostName);
        sb.append(":");
          switch (path){
            case "orders":
                sb.append(8081);
                break;
            case "customers":
                sb.append(8082);
                break;
            case "products":
                sb.append(8083);
                break;
        }
        sb.append("/");
        sb.append(path);

        for (String pe: pathElements) {
            sb.append("/");
            sb.append(pe);
        }
        String s = sb.toString();
        return s;
    }

    @Autowired
    RestTemplate restTemplate;

    //
    // 1st TEST
    // Approved order
    //
    @Test
    public void shouldBeAbleToPlaceAnOrder() {
        CreateCustomerResponse createCustomerResponse = restTemplate.postForObject(baseUrl("customers"),
            new CreateCustomerRequest(CUSTOMER_NAME, new Money("1500.00")), CreateCustomerResponse.class);

        CreateProductResponse createProductResponse = restTemplate.postForObject(baseUrl("products"),
            new CreateProductRequest(PRODUCT_NAME, new Stock(20), PRODUCT_DESCRIPTION), CreateProductResponse.class);

        CreateOrderResponse createOrderResponse = restTemplate.postForObject(baseUrl("orders"),
            new CreateOrderRequest(createCustomerResponse.getCustomerId(), new Money("1499.99"), createProductResponse.getProductId(), new Stock(19)), CreateOrderResponse.class);

        assertOrderState(createOrderResponse.getOrderId(), OrderState.APPROVED, null);
    }


    //
    // 2nd TEST
    // Failed - Insufficient Money
    //
    @Test
    public void shouldNotBeAbleToPlaceAnOrderBecauseOfInsufficientCredit() {
        CreateCustomerResponse createCustomerResponse = restTemplate.postForObject(baseUrl("customers"),
            new CreateCustomerRequest(CUSTOMER_NAME, new Money("1000.00")), CreateCustomerResponse.class);

        CreateProductResponse createProductResponse = restTemplate.postForObject(baseUrl("products"),
            new CreateProductRequest(PRODUCT_NAME, new Stock(20), PRODUCT_DESCRIPTION), CreateProductResponse.class);

        CreateOrderResponse createOrderResponse = restTemplate.postForObject(baseUrl("orders"),
            new CreateOrderRequest(createCustomerResponse.getCustomerId(), new Money("1499.99"), createProductResponse.getProductId(), new Stock(1)), CreateOrderResponse.class);

        assertOrderState(createOrderResponse.getOrderId(), OrderState.REJECTED, RejectionReason.INSUFFICIENT_CREDIT);
    }

    //
    // 2nd TEST
    // Failed - Insufficient Money
    //
    @Test
    public void shouldNotBeAbleToPlaceAnOrderBecauseOfInsufficientStock() {
        CreateCustomerResponse createCustomerResponse = restTemplate.postForObject(baseUrl("customers"),
            new CreateCustomerRequest(CUSTOMER_NAME, new Money("1000.00")), CreateCustomerResponse.class);

        CreateProductResponse createProductResponse = restTemplate.postForObject(baseUrl("products"),
            new CreateProductRequest(PRODUCT_NAME, new Stock(20), PRODUCT_DESCRIPTION), CreateProductResponse.class);

        CreateOrderResponse createOrderResponse = restTemplate.postForObject(baseUrl("orders"),
            new CreateOrderRequest(createCustomerResponse.getCustomerId(), new Money("1499.99"), createProductResponse.getProductId(), new Stock(1)), CreateOrderResponse.class);

        assertOrderState(createOrderResponse.getOrderId(), OrderState.REJECTED, RejectionReason.INSUFFICIENT_CREDIT);
    }

    //
    // 3rd TEST
    // Failed - Insufficient Stock
    //
    @Test
    public void shouldRejectBecauseOfInsufficientStock() {
         CreateCustomerResponse createCustomerResponse = restTemplate.postForObject(baseUrl("customers"),
            new CreateCustomerRequest(CUSTOMER_NAME, new Money("3000.00")), CreateCustomerResponse.class);

        CreateProductResponse createProductResponse = restTemplate.postForObject(baseUrl("products"),
            new CreateProductRequest(PRODUCT_NAME, new Stock(1), PRODUCT_DESCRIPTION), CreateProductResponse.class);

        CreateOrderResponse createOrderResponse = restTemplate.postForObject(baseUrl("orders"),
            new CreateOrderRequest(createCustomerResponse.getCustomerId(), new Money("1499.99"), createProductResponse.getProductId(), new Stock(2)), CreateOrderResponse.class);


        assertOrderState(createOrderResponse.getOrderId(), OrderState.REJECTED, RejectionReason.INSUFICIENT_STOCK);
    }

    private void assertOrderState(Long id, OrderState expectedState, RejectionReason expectedRejectionReason) {
        Eventually.eventually(() -> {
            ResponseEntity < GetOrderResponse > getOrderResponseEntity = restTemplate.getForEntity(baseUrl("orders", id.toString()), GetOrderResponse.class);
            assertEquals(HttpStatus.OK, getOrderResponseEntity.getStatusCode());
            GetOrderResponse order = getOrderResponseEntity.getBody();
            assertEquals(expectedState, order.getOrderState());
            assertEquals(expectedRejectionReason, order.getRejectionReason());
        });
    }

}
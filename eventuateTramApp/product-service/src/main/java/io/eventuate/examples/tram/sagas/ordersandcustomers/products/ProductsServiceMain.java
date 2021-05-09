package io.eventuate.examples.tram.sagas.ordersandcustomers.products;

import io.eventuate.examples.tram.sagas.ordersandcustomers.products.web.ProductWebConfiguration;
import io.eventuate.tram.spring.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration;
import io.eventuate.tram.spring.messaging.producer.jdbc.TramMessageProducerJdbcConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Configuration
@Import({ProductConfiguration.class,
        ProductWebConfiguration.class,
        TramMessageProducerJdbcConfiguration.class,
        EventuateTramKafkaMessageConsumerConfiguration.class})
@ComponentScan
public class ProductsServiceMain {

  public static void main(String[] args) {
    SpringApplication.run(ProductsServiceMain.class, args);
  }
}

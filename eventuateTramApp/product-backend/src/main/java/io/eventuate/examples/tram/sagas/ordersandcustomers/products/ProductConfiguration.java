package io.eventuate.examples.tram.sagas.ordersandProducts.products;

import io.eventuate.examples.tram.sagas.ordersandProducts.products.domain.ProductRepository;
import io.eventuate.examples.tram.sagas.ordersandProducts.products.service.ProductCommandHandler;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.service.ProductService;
import io.eventuate.tram.commands.product.CommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;
import io.eventuate.tram.sagas.spring.participant.SagaParticipantConfiguration;
import io.eventuate.tram.spring.optimisticlocking.OptimisticLockingDecoratorConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Import({SagaParticipantConfiguration.class, OptimisticLockingDecoratorConfiguration.class})
@EnableJpaRepositories
@EnableAutoConfiguration
public class ProductConfiguration {

  @Bean
  public ProductService productService(ProductRepository productRepository) {
    return new ProductService(productRepository);
  }

  @Bean
  public ProductCommandHandler productCommandHandler(ProductService productService) {
    return new ProductCommandHandler(productService);
  }

  // TODO Exception handler for ProductCreditLimitExceededException

  @Bean
  public CommandDispatcher productCommandDispatcher(ProductCommandHandler target,
                                                     SagaCommandDispatcherFactory sagaCommandDispatcherFactory) {

    return sagaCommandDispatcherFactory.make("productCommandDispatcher", target.commandHandlerDefinitions());
  }

}

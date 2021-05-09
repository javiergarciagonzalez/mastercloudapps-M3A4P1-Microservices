package io.eventuate.examples.tram.sagas.ordersandcustomers.products.service;

import io.eventuate.examples.tram.sagas.ordersandcustomers.products.api.commands.UpdateStockCommand;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.api.replies.ProductNotFound;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.api.replies.ProductStockLimitExceeded;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.domain.ProductNotFoundException;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.domain.ProductStockLimitExceededException;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

public class ProductCommandHandler {

  private ProductService productService;

  public ProductCommandHandler(ProductService productService) {
    this.productService = productService;
  }

  public CommandHandlers commandHandlerDefinitions() {
    return SagaCommandHandlersBuilder
            .fromChannel("productService")
            .onMessage(UpdateStockCommand.class, this::updateStock)
            .build();
  }

  public Message updateStock(CommandMessage<UpdateStockCommand> cm) {
    UpdateStockCommand cmd = cm.getCommand();

    try {
      productService.updateStock(cmd.getProductId(), cmd.getStock());
      return withSuccess(new ProductStockReserved());
    } catch (ProductNotFoundException e) {
      return withFailure(new ProductNotFound());
    } catch (ProductStockLimitExceededException e) {
      return withFailure(new ProductStockLimitExceeded());
    }
  }

}

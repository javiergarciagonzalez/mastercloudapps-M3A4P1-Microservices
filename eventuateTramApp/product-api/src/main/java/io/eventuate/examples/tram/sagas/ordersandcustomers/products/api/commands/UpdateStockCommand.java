package io.eventuate.examples.tram.sagas.ordersandcustomers.products.api.commands;

import io.eventuate.tram.commands.common.Command;

public class UpdateStockCommand implements Command {

    private Product product;

    public UpdateStockCommand(Product product) {
        this.product = product;
    }

    };
}

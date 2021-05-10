package io.eventuate.examples.tram.sagas.ordersandcustomers.customers.api.commands;

import io.eventuate.tram.commands.common.Command;

public class ReleaseCreditCommand implements Command {
    private Long orderId;
    private long customerId;

    public ReleaseCreditCommand() {
    }

    public ReleaseCreditCommand(Long customerId, Long orderId) {
        this.customerId = customerId;
        this.orderId = orderId;
    }

    public Long getOrderId() {

        return orderId;
    }

    public void setOrderId(Long orderId) {

        this.orderId = orderId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
}

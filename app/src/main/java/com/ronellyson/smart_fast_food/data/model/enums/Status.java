package com.ronellyson.smart_fast_food.data.model.enums;

public enum Status {
    PENDING(0, "Pendente"),
    INPROGRESS(1, "Em andamento"),
    DELIVERED(2, "Entregue");

    private int cod;
    private String name;

    Status(int cod, String name) {
        this.cod = cod;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

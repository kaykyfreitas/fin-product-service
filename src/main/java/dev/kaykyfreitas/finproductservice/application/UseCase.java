package dev.kaykyfreitas.finproductservice.application;

public abstract class UseCase<IN, OUT> {
    public abstract OUT execute(IN command);
}

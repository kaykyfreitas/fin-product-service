package dev.kaykyfreitas.finproductservice.application;

public abstract class UnitUseCase<IN> {
    public abstract void execute(IN command);
}

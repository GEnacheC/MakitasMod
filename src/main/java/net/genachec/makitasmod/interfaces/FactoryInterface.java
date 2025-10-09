package net.genachec.makitasmod.interfaces;

public interface FactoryInterface<T extends StrategyInterface, E> {
    T create(E type);
}

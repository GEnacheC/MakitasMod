package net.genachec.makitasmod.factories.ai;

import net.genachec.makitasmod.enums.EObstacleType;
import net.genachec.makitasmod.interfaces.FactoryInterface;
import net.genachec.makitasmod.interfaces.ai.StrategyPassObstacleInterface;
import net.genachec.makitasmod.strategies.ai.StrategyPassAir;
import net.genachec.makitasmod.strategies.ai.StrategyPassWall;

public class FactoryPassObstacle implements FactoryInterface<StrategyPassObstacleInterface, EObstacleType> {

    @Override
    public StrategyPassObstacleInterface create(EObstacleType type) {
        return switch (type) {
            case AIR -> new StrategyPassAir();
            case WALL -> new StrategyPassWall();
            default -> throw new IllegalArgumentException("Tipo de obstáculo não suportado: " + type);
        };
    }
}

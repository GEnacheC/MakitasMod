package net.genachec.makitasmod.interfaces.ai;

import net.genachec.makitasmod.interfaces.StrategyInterface;
import net.minecraft.world.entity.PathfinderMob;

import javax.naming.OperationNotSupportedException;

public interface StrategyPassObstacleInterface extends StrategyInterface {
    boolean passObstacle(PathfinderMob mob);
}

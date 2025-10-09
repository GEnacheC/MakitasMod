package net.genachec.makitasmod.strategies.ai;

import net.genachec.makitasmod.interfaces.ai.StrategyPassObstacleInterface;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;

public class StrategyPassAir implements StrategyPassObstacleInterface {

    @Override
    public boolean passObstacle(PathfinderMob mob) {
        Vec3 lookAngle = mob.getLookAngle();
        double jumpPower = 0.4;
        mob.setDeltaMovement(
                lookAngle.x * jumpPower,
                jumpPower,
                lookAngle.z * jumpPower);

        return true;
    }
}

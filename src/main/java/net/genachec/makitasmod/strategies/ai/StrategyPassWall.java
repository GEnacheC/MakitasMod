package net.genachec.makitasmod.strategies.ai;

import net.genachec.makitasmod.interfaces.ai.StrategyPassObstacleInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;

public class StrategyPassWall implements StrategyPassObstacleInterface {

    @Override
    public boolean passObstacle(PathfinderMob mob) {
        Vec3 lookAngle = mob.getLookAngle();
        double teleportDistance = 3.0;

        double newX = mob.getX() + lookAngle.x * teleportDistance;
        double newY = mob.getY() + lookAngle.y * teleportDistance;
        double newZ = mob.getZ() + lookAngle.z * teleportDistance;

        BlockPos safePos = findSafeTeleportPosition(
                mob,
                new BlockPos((int) newX, (int) newY, (int) newZ));

        if (safePos != null) {
            mob.teleportTo(
                    safePos.getX() + 0.5,
                    safePos.getY(),
                    safePos.getZ() + 0.5
            );

            mob.setDeltaMovement(
                    lookAngle.x * 0.2,
                    lookAngle.y * 0.2,
                    lookAngle.z * 0.2
            );
        }

        return true;
    }

    private BlockPos findSafeTeleportPosition(PathfinderMob mob, BlockPos targetPos) {
        for (int yOffset = -2; yOffset <= 3; yOffset++) {
            BlockPos checkPos = targetPos.above(yOffset);

            if (isPositionSafeForTeleport(mob, checkPos)) {
                return checkPos;
            }
        }

        return null;
    }

    private boolean isPositionSafeForTeleport(PathfinderMob mob, BlockPos pos) {
        boolean positionSafe = mob.level().getBlockState(pos).isAir() &&
                mob.level().getBlockState(pos.above()).isAir();

        boolean hasSolidGround = !mob.level().getBlockState(pos.below()).isAir();

        return positionSafe && hasSolidGround;
    }
}

package net.genachec.makitasmod.entity.ai;

import com.mojang.logging.LogUtils;
import net.genachec.makitasmod.enums.EObstacleType;
import net.genachec.makitasmod.factories.ai.FactoryPassObstacle;
import net.genachec.makitasmod.interfaces.ai.StrategyPassObstacleInterface;
import net.genachec.makitasmod.util.MakitasModTags;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.BlockPos;
import org.slf4j.Logger;

import java.util.EnumSet;

public class SearchMakitaBlockGoal extends Goal {
    private final PathfinderMob mob;
    private final double speedModifier;
    private final double detectionRange;
    private BlockPos targetBlockPos;
    private boolean isTeleporting = false;

    // Obstacle handlers
    private EObstacleType obstacle = EObstacleType.NONE;
    private int timeStopedInTicks = 0;
    private int timeHandlingObstacle = 0;
    private boolean canPassObstacle = true;

    private int timeCoolDownVerifyPathTicks = 0;

    private static final Logger LOGGER = LogUtils.getLogger();

    public SearchMakitaBlockGoal(PathfinderMob mob) {
        this.mob = mob;
        this.speedModifier = 1.2;
        this.detectionRange = 24;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        this.targetBlockPos = findNearestMakitaBlock();
        return this.targetBlockPos != null;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.targetBlockPos == null) {
            return false;
        }

        return !reachedTarget();
    }

    @Override
    public void start() {
        if (this.targetBlockPos != null) {
            determineMovementType();
        }
    }

    @Override
    public void tick() {
        if (this.targetBlockPos == null) return;

        if(reachedTarget()) {
            this.mob.kill();
            return;
        }

        lookAtTarget();
        handleObstacle();
        determineMovementType();
        executeMovement();
    }

    @Override
    public void stop() {
        this.mob.getNavigation().stop();
        this.targetBlockPos = null;
        this.isTeleporting = false;
    }

    private BlockPos findNearestMakitaBlock() {
        BlockPos mobPos = this.mob.blockPosition();
        BlockPos nearestBlock = null;
        double nearestDistance = Double.MAX_VALUE;

        int range = (int) this.detectionRange;

        // Busca em um cubo ao redor do mob
        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos checkPos = mobPos.offset(x, y, z);

                    // Verifica se o bloco tem a tag MAKITAS_BLOCK
                    if (this.mob.level().getBlockState(checkPos).is(MakitasModTags.Blocks.MAKITAS_BLOCK)) {
                        double distance = mobPos.distSqr(checkPos);

                        if (distance < nearestDistance) {
                            nearestDistance = distance;
                            nearestBlock = checkPos;
                        }
                    }
                }
            }
        }

        return nearestBlock;
    }

    private void lookAtTarget() {
        this.mob.lookAt(EntityAnchorArgument.Anchor.EYES,
                new Vec3(
                        targetBlockPos.getX(),
                        targetBlockPos.getY(),
                        targetBlockPos.getZ()
                )
        );
    }

    private boolean reachedTarget() {
        double distance = this.mob.position().distanceTo(Vec3.atCenterOf(this.targetBlockPos));

        if (distance < 2.0) {
            LOGGER.info("Chegou no Objetivo!");
            return true;
        }

        if (!this.mob.level().getBlockState(this.targetBlockPos).is(MakitasModTags.Blocks.MAKITAS_BLOCK)) {
            return true;
        }

        return !(distance <= this.detectionRange * 1.5);
    }

    private EObstacleType checkObstacle() {
        if (!mob.getNavigation().isInProgress()) {
            timeStopedInTicks++;

            if(!isStoped()) return obstacle;
            boolean isWallInFront = isWallInFront();

            if (isWallInFront) {
                LOGGER.info("Mob está parado por causa de uma parede ao buscar bloco Makita!");
                return EObstacleType.WALL;
            } else {
                LOGGER.info("Mob está parado por causa de um buraco ao buscar bloco Makita!");
                return EObstacleType.AIR;
            }
        }

        canPassObstacle = true;
        timeStopedInTicks = 0;
        return EObstacleType.NONE;
    }

    private boolean isStoped() {
        int MAX_TIME_STOPED_TICKS = 8;
        return timeStopedInTicks >= MAX_TIME_STOPED_TICKS;
    }

    private boolean isHandlingObstacle() {
        int MAX_TIME_HANDLE_OBSTACLE_TICKS = 12;
        return timeHandlingObstacle > 0 && timeHandlingObstacle < MAX_TIME_HANDLE_OBSTACLE_TICKS;
    }

    private boolean handledObstacle() {
        int MAX_TIME_HANDLE_OBSTACLE_TICKS = 12;
        return timeHandlingObstacle >= MAX_TIME_HANDLE_OBSTACLE_TICKS;
    }


    private void handleObstacle() {
        this.obstacle = checkObstacle();
        if(obstacle == EObstacleType.NONE || !canPassObstacle) return;

        if(isHandlingObstacle()) {
            timeHandlingObstacle++;
            return;
        }

        if(handledObstacle()) {
            obstacle = EObstacleType.NONE;
            timeStopedInTicks = 0;
            timeHandlingObstacle = 0;
            return;
        }

        FactoryPassObstacle factory = new FactoryPassObstacle();

        StrategyPassObstacleInterface strategy = factory.create(obstacle);

        canPassObstacle = strategy.passObstacle(mob);

        if(!canPassObstacle) {
            LOGGER.info("O mob está preso");
            return;
        }

        timeHandlingObstacle++;
    }

    private void executeMovement() {
        if (this.targetBlockPos == null) return;

        if (isTeleporting) {
            this.mob.getNavigation().stop();
            this.mob.teleportTo(
                    this.mob.getX(),
                    this.targetBlockPos.getY(),
                    this.mob.getZ()
            );
            isTeleporting = false;
        } else {
            this.mob.getNavigation().moveTo(
                    this.targetBlockPos.getX() + 0.5,
                    this.targetBlockPos.getY(),
                    this.targetBlockPos.getZ() + 0.5,
                    this.speedModifier
            );
        }
    }

    private void determineMovementType() {
        if (this.targetBlockPos == null) return;

        BlockPos mobPos = this.mob.blockPosition();

        boolean isAbove = mobPos.getY() > this.targetBlockPos.getY();
        boolean isBelow = mobPos.getY() < this.targetBlockPos.getY();
        int heightDifference = Math.abs(mobPos.getY() - this.targetBlockPos.getY());

        BlockPos checkPos = isAbove ?
                mobPos.below(heightDifference + 1) :
                mobPos.above(heightDifference - 1);

        this.isTeleporting = (isAbove || isBelow) &&
                heightDifference > 2 &&
                !this.mob.level().getBlockState(checkPos).isAir();

        if (!this.isTeleporting) {
            this.mob.getNavigation().stop();
        }
    }

    private boolean isWallInFront() {
        Vec3 lookAngle = this.mob.getLookAngle();

        int xOffset = lookAngle.x > 0.5 ? 1 : (lookAngle.x < -0.5 ? -1 : 0);
        int zOffset = lookAngle.z > 0.5 ? 1 : (lookAngle.z < -0.5 ? -1 : 0);

        BlockPos frontPos = this.mob.blockPosition().offset(xOffset, 0, zOffset);
        BlockPos frontPosAbove = frontPos.above();

        boolean hasWallAtLevel = !this.mob.level().getBlockState(frontPos).isAir();
        boolean hasWallAbove = !this.mob.level().getBlockState(frontPosAbove).isAir();

        return hasWallAtLevel || hasWallAbove;
    }

}
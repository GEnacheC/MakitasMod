package net.genachec.makitasmod.entity.ai;

import net.genachec.makitasmod.util.MakitasModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;


public class SearchMakitaBlockGoal extends Goal {
    private final PathfinderMob mob;
    private final TagKey<Block> blockTag;
    private final double speedModifier;
    private final int searchRange;
    private BlockPos targetPos;


    public SearchMakitaBlockGoal(PathfinderMob mob) {
        this.mob = mob;
        this.blockTag = MakitasModTags.Blocks.MAKITAS_BLOCK;
        this.speedModifier = 1;
        this.searchRange = 16;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        this.targetPos = findNearestBlockWithTag();
        return this.targetPos != null;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.targetPos == null) {
            return false;
        }

        if (!this.mob.level().getBlockState(this.targetPos).is(this.blockTag)) {
            this.targetPos = null;
            return false;
        }

        return true;
    }

    @Override
    public void start() {
        if (this.targetPos != null) {
            this.mob.getNavigation().moveTo(
                    this.targetPos.getX() + 0.5,
                    this.targetPos.getY(),
                    this.targetPos.getZ() + 0.5,
                    this.speedModifier
            );
        }
    }

    @Override
    public void tick() {
        if (this.targetPos != null) {
            double distance = this.mob.position().distanceTo(
                    new Vec3(this.targetPos.getX() + 0.5,
                            this.targetPos.getY(),
                            this.targetPos.getZ() + 0.5)
            );

            if (distance < 2.0) {
                BlockPos newPos = findNearestBlockWithTag();
                if (newPos != null && !newPos.equals(this.targetPos)) {
                    this.targetPos = newPos;
                }
            }

            this.mob.getNavigation().moveTo(
                    this.targetPos.getX() + 0.5,
                    this.targetPos.getY(),
                    this.targetPos.getZ() + 0.5,
                    this.speedModifier
            );
        }
    }

    @Override
    public void stop() {
        this.mob.getNavigation().stop();
        this.targetPos = null;
    }

    private BlockPos findNearestBlockWithTag() {
        BlockPos mobPos = this.mob.blockPosition();
        BlockPos nearestPos = null;
        double nearestDistance = Double.MAX_VALUE;

        for (int x = -this.searchRange; x <= this.searchRange; x++) {
            for (int y = -this.searchRange; y <= this.searchRange; y++) {
                for (int z = -this.searchRange; z <= this.searchRange; z++) {
                    BlockPos checkPos = mobPos.offset(x, y, z);

                    if (this.mob.level().getBlockState(checkPos).is(this.blockTag)) {
                        double distance = mobPos.distSqr(checkPos);

                        if (distance < nearestDistance) {
                            nearestDistance = distance;
                            nearestPos = checkPos;
                        }
                    }
                }
            }
        }

        return nearestPos;
    }
}

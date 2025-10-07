package net.genachec.makitasmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MakitaBlock extends HorizontalDirectionalBlock {

    public MakitaBlock() {
        super(
                BlockBehaviour.Properties.copy(Blocks.ANVIL)
                        .strength(4.0F, 1200.0F)
                        .noOcclusion()
        );
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        VoxelShape shape = Block.box(0.5D, 0.0D, 3.0D, 15.5D, 12.0D, 14.0D);
        switch (facing) {
            case SOUTH -> shape = Block.box(0.5D, 0.0D, 2.0D, 15.5D, 12.0D, 13.0D);
            case WEST  -> shape = Block.box(3.0D, 0.0D, 0.5D, 14.0D, 12.0D, 15.5D);
            case EAST  -> shape = Block.box(2.0D, 0.0D, 0.5D, 13.0D, 12.0D, 15.5D);
            default -> {}
        }

        return shape;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos,
                                 Player player, InteractionHand hand, BlockHitResult hit) {
        if (!world.isClientSide) {
            player.sendSystemMessage(Component.literal("Os cara tão no teto..."));
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

}
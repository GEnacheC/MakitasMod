package net.genachec.makitasmod.block.custom;

import net.genachec.makitasmod.sound.MakitasModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class MakitaBlock extends HorizontalDirectionalBlock {
    public static final BooleanProperty ON = BooleanProperty.create("on");
    private static final int RANGE = 5;
    private static final int TICK_DELAY = 100;
    public MakitaBlock() {
        super(
                BlockBehaviour.Properties.copy(Blocks.ANVIL)
                        .strength(4.0F, 1200.0F)
                        .noOcclusion()
        );
        this.registerDefaultState(this.defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(ON, false)
        );
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
            boolean newState = !state.getValue(ON);
            world.setBlock(pos, state.setValue(ON, newState), 3);

            world.playSound(null, pos, MakitasModSounds.MAKITA_SWITCH.get(), SoundSource.BLOCKS, 0.7f, 0.7f);
            if (newState){
                world.playSound(null, pos, MakitasModSounds.MAKITA_OMINOUS.get(), SoundSource.BLOCKS, 0.5f, 0.5f);
                player.sendSystemMessage(Component.literal("Os cara tão no teto..."));
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, ON);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (state.getValue(MakitaBlock.ON)) {
            List<Player> players = world.getEntitiesOfClass(Player.class,
                    new AABB(pos.offset(-RANGE, -RANGE, -RANGE), pos.offset(RANGE + 1, RANGE + 1, RANGE + 1)));

            for (Player player : players) {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, TICK_DELAY, 1, false, false, true));
            }
        }

        world.scheduleTick(pos, this, TICK_DELAY);
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!world.isClientSide) {
            world.scheduleTick(pos, this, 20);
        }
    }
}
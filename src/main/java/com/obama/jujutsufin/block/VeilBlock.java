package com.obama.jujutsufin.block;

import com.obama.jujutsufin.init.JujutsufinEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class VeilBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(0,0,0,16,16,16);

    public VeilBlock() {
        super(Properties.of()
                .lightLevel(state -> 15)
                .noLootTable().strength(-1F, Float.MAX_VALUE)
                .pushReaction(PushReaction.IGNORE)
                .noOcclusion()
                .instrument(NoteBlockInstrument.BANJO)
                .isSuffocating((blockState, blockGetter, blockPos) -> {
                    BlockEntity blockEntity = blockGetter.getBlockEntity(blockPos);
                    if (blockEntity != null) {
                        return blockEntity.getPersistentData().getBoolean("Collision");
                    }
                    return true;
                })
                .isViewBlocking((blockState, blockGetter, blockPos) -> false)
        );
    }

    @Override
    public boolean propagatesSkylightDown(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new VeilBlockEntity(blockPos, blockState);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        BlockEntity blockEntity = blockGetter.getBlockEntity(blockPos);
        if (blockEntity != null && !blockEntity.getPersistentData().getBoolean("Collision")) {
            return Block.box(0,0,0,0,0,0);
        }
        return SHAPE;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide) return null;

        return createTickerHelper(blockEntityType, JujutsufinEntities.VeilBlock.get(),
                (level1, blockPos, blockState1, veilBlockEntity) -> veilBlockEntity.tick(level1, blockPos, blockState1)
        );
    }
}

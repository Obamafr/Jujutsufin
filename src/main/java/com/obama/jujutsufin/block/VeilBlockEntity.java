package com.obama.jujutsufin.block;

import com.obama.jujutsufin.init.JujutsufinEntities;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class VeilBlockEntity extends BlockEntity {
    private final ItemStackHandler itemStackHandler = new ItemStackHandler(0);
    private LazyOptional<IItemHandler> LazyHandler = LazyOptional.empty();

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return LazyHandler.cast();
        }
        return super.getCapability(cap);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        LazyHandler = LazyOptional.of(() -> itemStackHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        LazyHandler.invalidate();
    }

    public VeilBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(JujutsufinEntities.VeilBlock.get(), blockPos, blockState);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        for (Entity entity : level.getEntitiesOfClass(Entity.class, new AABB(blockPos).inflate(1))) {
            CompoundTag tag = getPersistentData().getCompound("Veils");
            int Technique2 = (int) entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCurseTechnique2;
            if (Technique2 == -1 || entity.getPersistentData().getDouble("friend_num") == getPersistentData().getDouble("friend_num")) {
                getPersistentData().putBoolean("Collision", false);
                continue;
            }
            if (tag.getBoolean("P1") && entity.getPersistentData().getBoolean("CursedSpirit")
                    || tag.getBoolean("P2") && entity.getPersistentData().getBoolean("CurseUser")
                    || tag.getBoolean("P3") && entity.getPersistentData().getBoolean("JujutsuSorcerer")
                    || tag.getInt("P4") == Technique2) {
                getPersistentData().putBoolean("Collision", !getPersistentData().getString("veilOwner").equals(entity.getStringUUID()));
                break;
            } else {
                getPersistentData().putBoolean("Collision", false);
            }
        }
    }
}

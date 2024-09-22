package com.obama.jujutsufin.entity;

import com.obama.jujutsufin.init.JujutsufinBlocks;
import com.obama.jujutsufin.techniques.veils.VeilThread;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nullable;

public class VeilEntity extends Mob {
    private boolean madeVeil;
    private int VeilX;
    private int VeilY;
    private int VeilZ;

    public VeilEntity(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
        this.setNoAi(true);
        this.madeVeil = false;
        this.setPersistenceRequired();
    }

    @Override
    public boolean removeWhenFarAway(double distance) {
        return false;
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor accessor, @NotNull DifficultyInstance difficultyInstance, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag tag) {
        SpawnGroupData spawnGroupData1 = super.finalizeSpawn(accessor, difficultyInstance, spawnType, spawnGroupData, tag);
        getPersistentData().putInt("veilX", (int) getX());
        getPersistentData().putInt("veilY", (int) getY());
        getPersistentData().putInt("veilZ", (int) getZ());
        VeilX = getPersistentData().getInt("veilX");
        VeilY = getPersistentData().getInt("veilY");
        VeilZ = getPersistentData().getInt("veilZ");
        setMadeVeil(true);
        return spawnGroupData1;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        madeVeil = getPersistentData().getBoolean("madeVeil");
        VeilX = getPersistentData().getInt("veilX");
        VeilY = getPersistentData().getInt("veilY");
        VeilZ = getPersistentData().getInt("veilZ");
        if (getPersistentData().getBoolean("break")) {
            setMadeVeil(false);
            discard();
        }
    }

    public boolean isMadeVeil() {
        return madeVeil;
    }

    public void setMadeVeil(boolean madeVeil) {
        if (level() instanceof ServerLevel serverLevel) {
            VeilThread thread = new VeilThread(serverLevel, this, madeVeil);
            thread.start();
            getPersistentData().putBoolean("madeVeil", madeVeil);
            this.madeVeil = madeVeil;
        }
    }

    public void makeVeil(ServerLevel serverLevel, int x, int y, int z) {
        BlockPos blockPos = BlockPos.containing(x, y, z);
        BlockState oldBlock = serverLevel.getBlockState(blockPos);
        if (oldBlock.is(BlockTags.create(new ResourceLocation("jujutsucraft:barrier")))) return;
        BlockEntity oldBlockEntity = serverLevel.getBlockEntity(blockPos);
        ListTag items = new ListTag();
        if (oldBlockEntity != null) {
            oldBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(oBlockHandler -> {
                for (int i = 0; i < oBlockHandler.getSlots(); i++) {
                    ItemStack itemStack = oBlockHandler.getStackInSlot(i);
                    if (itemStack.getItem() != Items.AIR) {
                        CompoundTag compoundTag = new CompoundTag();
                        itemStack.save(compoundTag);
                        compoundTag.putInt("Slot", i);
                        items.add(compoundTag);
                    }
                }
            });
        }
        serverLevel.getServer().getCommands().performPrefixedCommand(createCommandSourceStack().withPermission(4).withSuppressedOutput(), "setblock " + x + " " + y + " " + z + " jujutsufin:veil");
        String oldBlockInfo = oldBlock.toString();
        oldBlockInfo = oldBlockInfo.replace("Block{", "");
        oldBlockInfo = oldBlockInfo.replace("}", "");
        BlockEntity newBlockEntity = serverLevel.getBlockEntity(blockPos);
        if (newBlockEntity != null) {
            CompoundTag data = newBlockEntity.getPersistentData();
            data.put("oldBlockItems", items);
            data.putString("old_block", oldBlockInfo);
            data.put("Veils", getPersistentData().getCompound("Veils"));
            data.putString("veilOwner", getPersistentData().getString("veilOwner"));
            data.putDouble("friend_num", getPersistentData().getDouble("friend_num"));
        }
    }

    public void breakVeil(ServerLevel serverLevel, int x, int y, int z) {
        BlockPos blockPos = BlockPos.containing(x, y, z);
        BlockEntity oldBlockEntity = serverLevel.getBlockEntity(blockPos);
        if (oldBlockEntity != null) {
            ListTag items = oldBlockEntity.getPersistentData().getList("oldBlockItems", 10);
            serverLevel.getServer().getCommands().performPrefixedCommand(createCommandSourceStack().withPermission(4).withSuppressedOutput(), "setblock " + x + " " + y + " " + z + " " + oldBlockEntity.getPersistentData().getString("old_block"));
            BlockEntity newBlockEntity = serverLevel.getBlockEntity(blockPos);
            if (newBlockEntity != null) {
                newBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(nItemHandler -> {
                    for (Tag item : items) {
                        if (item instanceof CompoundTag tag) {
                            nItemHandler.insertItem(tag.getInt("Slot"), ItemStack.of(tag), false);
                        }
                    }
                });
            }
        }
    }


    public int getVeilX() {
        return VeilX;
    }

    public int getVeilY() {
        return VeilY;
    }

    public int getVeilZ() {
        return VeilZ;
    }

    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if (damageSource.is(DamageTypes.GENERIC_KILL)) {
            return super.hurt(damageSource, amount);
        } else {
            return false;
        }
    }

    @Override
    protected void doPush(@NotNull Entity entityIn) {
    }

    @Override
    protected void pushEntities() {
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMobAttributes().add(Attributes.MAX_HEALTH, 200)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1)
                .add(ForgeMod.ENTITY_GRAVITY.get(), 0);
    }
}

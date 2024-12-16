package com.obama.jujutsufin.entity;

import com.obama.jujutsufin.init.JujutsufinEntities;
import net.mcreator.jujutsucraft.init.JujutsucraftModBlocks;
import net.mcreator.jujutsucraft.init.JujutsucraftModParticleTypes;
import net.mcreator.jujutsucraft.procedures.BlockDestroyAllDirectionProcedure;
import net.mcreator.jujutsucraft.procedures.LogicAttackProcedure;
import net.mcreator.jujutsucraft.procedures.SetRangedAmmoProcedure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class PigeonViola extends Mob {

    public static final EntityDataAccessor<Integer> colorType = SynchedEntityData.defineId(PigeonViola.class, EntityDataSerializers.INT);
    private LivingEntity Owner;

    public PigeonViola(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
        super.noPhysics = true;
    }

    public PigeonViola(Level level) {
        super(JujutsufinEntities.Viola.get(), level);
        super.noPhysics = true;
    }

    @Override
    public void tick() {
        super.tick();
        int cntA = this.getPersistentData().getInt("cntA");
        cntA++;
        this.getPersistentData().putInt("cntA", cntA);
        if (cntA == this.getPersistentData().getInt("timer")) {
            this.level().playSound(null, this, SoundEvents.WITHER_SHOOT, SoundSource.PLAYERS, 1, 1);
        }
        this.getPersistentData().putBoolean("LAUNCH", cntA >= this.getPersistentData().getInt("timer"));
        if (this.getPersistentData().getBoolean("LAUNCH")) {
            if (this.level() instanceof ServerLevel serverLevel) {
                serverLevel.playSound(null, this, SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1, 1);
                if (cntA % 10 == 0) {
                    serverLevel.sendParticles(JujutsucraftModParticleTypes.PARTICLE_BIG_SMOKE.get(), this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 1);
                }
            }
        }
        if (this.getTarget() != null && this.getTarget().isAlive() && this.getPersistentData().getBoolean("LAUNCH")) {
            Vec3 direction = this.getTarget().position().subtract(this.position()).normalize();
            this.setDeltaMovement(direction.scale(1));
        } else if (this.getPersistentData().getBoolean("LAUNCH")) {
            this.setDeltaMovement(this.getOwner().getLookAngle().scale(2));
        } else {
            MinecraftServer server = this.level().getServer();
            if (server != null) {
                server.getCommands().performPrefixedCommand(this.getOwner().createCommandSourceStack().withPermission(4).withSuppressedOutput(), "tp " + this.getStringUUID() + this.getPersistentData().getString("teleport"));
            }
        }
        BlockState blockState = this.level().getBlockState(this.blockPosition());
        if (blockState != Blocks.AIR.defaultBlockState() && blockState != JujutsucraftModBlocks.IN_BARRIER.get().defaultBlockState()) {
            this.explode();
        }
        if (!this.level().getEntitiesOfClass(Entity.class, new AABB(this.blockPosition()), (p) -> p == this.getTarget()).isEmpty() || cntA == 400) {
            this.explode();
        }
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean canCollideWith(@NotNull Entity entity) {
        return false;
    }

    @Override
    public @Nullable LivingEntity getTarget() {
        return super.getTarget();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(colorType, 1);
    }

    public void explode() {
        float damage = 8;
        MobEffectInstance strength = this.getEffect(MobEffects.DAMAGE_BOOST);
        if (strength != null) {
            damage *= strength.getAmplifier();
        }
        for (Entity entityIterator : this.level().getEntitiesOfClass(Entity.class, new AABB(this.position(), this.position()).inflate(8), (p) -> p != this && LogicAttackProcedure.execute(this.level(), this.getOwner(), p))) {
            entityIterator.hurt(new DamageSource(this.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("jujutsucraft:damage_curse"))), this.getOwner()), damage);
        }
        this.getPersistentData().putDouble("BlockRange", 4);
        this.getPersistentData().putDouble("BlockDamage", Double.MAX_VALUE);
        this.getPersistentData().putBoolean("noParticle", true);
        this.getPersistentData().putBoolean("ExtinctionBlock", true);
        BlockDestroyAllDirectionProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
        this.level().playSound(null, this, SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1, 1);
        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.EXPLOSION_EMITTER, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 1);
        }
        this.discard();
    }

    private LivingEntity getOwner() {
        return Objects.requireNonNullElse(Owner, this);
    }

    @Override
    public boolean hurt(@NotNull DamageSource damageSource, float v) {
        return false;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    public void setOwner(LivingEntity livingEntity) {
        this.Owner = livingEntity;
    }
}

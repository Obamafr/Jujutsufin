package com.obama.jujutsufin.entity;

import com.obama.jujutsufin.init.JujutsufinEntities;
import com.obama.jujutsufin.init.JujutsufinParticles;
import net.mcreator.jujutsucraft.init.JujutsucraftModParticleTypes;
import net.mcreator.jujutsucraft.init.JujutsucraftModSounds;
import net.mcreator.jujutsucraft.procedures.*;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Map;


public class BeamProjectile extends AbstractHurtingProjectile {

    public static final EntityDataAccessor<Float> scale = SynchedEntityData.defineId(BeamProjectile.class, EntityDataSerializers.FLOAT);

    public BeamProjectile(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public BeamProjectile(Level level) {
        super(JujutsufinEntities.Beam.get(), level);
    }

    public BeamProjectile(EntityType<? extends AbstractHurtingProjectile> entityType, LivingEntity livingEntity, Vec3 lookAngle, Level level) {
        super(entityType, livingEntity, lookAngle.x, lookAngle.y, lookAngle.z, level);
    }

    public BeamProjectile(LivingEntity livingEntity, Vec3 lookAngle, Level level) {
        this(JujutsufinEntities.Beam.get(), livingEntity, lookAngle, level);
    }

    public BeamProjectile(LivingEntity livingEntity, Vec3 lookAngle, Level level, float scale) {
        this(JujutsufinEntities.Beam.get(), livingEntity, lookAngle, level);
        this.entityData.set(BeamProjectile.scale, scale);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(scale, 1f);
    }

    private void updateBoundingBox() {
        float scale = this.entityData.get(BeamProjectile.scale);
        double halfWidth = scale / 2;
        this.setBoundingBox(new AABB(
                this.getX() - halfWidth, this.getY(), this.getZ() - halfWidth,
                this.getX() + halfWidth, this.getY() + scale, this.getZ() + halfWidth
        ));
        this.refreshDimensions();
    }

    private static final Map<Long, Double> damageValues = Map.of(
            1L, 3d,
            2L, 3d,
            4L, 5d,
            8L, 8d
    );
    private static final Map<Long, Double> rangeValues = Map.of(
            1L, 4d,
            2L, 8d,
            4L, 12d,
            8L, 16d
    );

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return super.getDimensions(pose).scale(this.entityData.get(BeamProjectile.scale));
    }

    @Override
    public void baseTick() {
        super.baseTick();
        updateBoundingBox();
        int cntA = this.getPersistentData().getInt("cntA");
        cntA++;
        this.getPersistentData().putInt("cntA", cntA);
        double CNT6 = Math.max(this.getPersistentData().getDouble("cnt6"), 1);
        this.getPersistentData().putDouble("Damage", damageValues.getOrDefault(Math.round(getBoundingBox().getSize()), 3d) * CNT6);
        this.getPersistentData().putDouble("Range", rangeValues.getOrDefault(Math.round(getBoundingBox().getSize()), 4d));
        this.getPersistentData().putDouble("knockback", 2);
        DoAttack(this);
        if (cntA == 200) {
            this.discard();
        }
        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.playSound(null, this, SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1, 1);
            if (cntA % 5 == 0) {
                serverLevel.sendParticles(JujutsucraftModParticleTypes.PARTICLE_BIG_SMOKE.get(), this.getX(), this.getY(), this.getZ(), 5, 0, 0, 0, 1);
            }
        }
    }

    public static void DoAttack(BeamProjectile beamProjectile) {
        double range = beamProjectile.getPersistentData().getDouble("Range");
        for (int index1 = 0; index1 < 2; ++index1) {
            Vec3 _center = new Vec3(beamProjectile.getX(), beamProjectile.getY(), beamProjectile.getZ());
            Level world = beamProjectile.level();
            double x = beamProjectile.getX();
            double y = beamProjectile.getY();
            double z = beamProjectile.getX();
            for (Entity entityIterator : world.getEntitiesOfClass(Entity.class, (new AABB(_center, _center)).inflate(range / 2))) {
                boolean betrayal = LogicBetrayalProcedure.execute(beamProjectile.getOwner(), entityIterator);
                if (beamProjectile != entityIterator || betrayal) {
                    if (LogicAttackProcedure.execute(world, beamProjectile.getOwner(), entityIterator) || betrayal) {
                        EffectConfirmProcedure.execute(world, x, y, z, beamProjectile.getOwner(), entityIterator);
                        double damageSource = beamProjectile.getPersistentData().getDouble("Damage");
                        if (beamProjectile.getOwner() instanceof LivingEntity livingEntity) {
                            MobEffectInstance strength = livingEntity.getEffect(MobEffects.DAMAGE_BOOST);
                            if (strength != null) {
                                damageSource *= strength.getAmplifier();
                            }
                        }
                        entityIterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("jujutsucraft:damage_curse"))), beamProjectile.getOwner()), (float) damageSource);
                        entityIterator.level().playSound(null, entityIterator, JujutsucraftModSounds.CRUSH.get(), SoundSource.PLAYERS, 1, 1);
                    }
                }
            }
        }
    }

    @Override
    public boolean isInWater() {
        return false;
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        this.getPersistentData().putDouble("BlockRange", rangeValues.getOrDefault(Math.round(getBoundingBox().getSize()), 4d));
        this.getPersistentData().putDouble("BlockDamage", Double.MAX_VALUE);
        this.getPersistentData().putBoolean("noParticle", true);
        this.getPersistentData().putBoolean("ExtinctionBlock", true);
        BlockDestroyAllDirectionProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
    }

    @Override
    protected @NotNull ParticleOptions getTrailParticle() {
        return JujutsufinParticles.EMPTY.get();
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public boolean hurt(@NotNull DamageSource damageSource, float v) {
        return false;
    }

    @Override
    public void handleDamageEvent(@NotNull DamageSource damageSource) {}
}

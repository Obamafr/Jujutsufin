package com.obama.jujutsufin.effects;

import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.RangeAttackProcedure;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GravityEffect extends MobEffect {
    public GravityEffect() {
        super(MobEffectCategory.BENEFICIAL, 10596017);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amp) {
        CompoundTag originData = livingEntity.getPersistentData();
        if (livingEntity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCursePower < 5 || livingEntity.hasEffect(JujutsucraftModMobEffects.NEUTRALIZATION.get())) {
            livingEntity.removeEffect(this);
        }
        if (livingEntity.level() instanceof ServerLevel serverLevel) {
            List<Entity> entityList = serverLevel.getEntitiesOfClass(Entity.class, new AABB(livingEntity.blockPosition()).inflate(4));
            for (Entity entity : entityList) {
                if (entity instanceof Projectile || entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge:ranged_ammo"))))  {
                    if (entity.getPersistentData().getDouble("friend_num") != originData.getDouble("friend_num")) {
                        entity.setDeltaMovement(0, -2, 0);
                    }
                }
            }
            serverLevel.sendParticles(ParticleTypes.PORTAL, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 5, 2, 0, 2, 1);
            double cntGEft = originData.getDouble("cntGEft");
            originData.putDouble("cntGEft",  cntGEft + 1);
            if (cntGEft % 40 == 0 && cntGEft != 0) {
                originData.putDouble("Damage", 8);
                originData.putDouble("knockback", 0);
                originData.putDouble("Range", 4);
                serverLevel.playSound(null, livingEntity.blockPosition(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1, 1);
                RangeAttackProcedure.execute(serverLevel, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), livingEntity);
            }
        }
        livingEntity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(cap -> {
            cap.PlayerCursePowerChange -= 1;
            cap.syncPlayerVariables(livingEntity);
        });
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity livingEntity, @NotNull AttributeMap map, int a) {
        super.removeAttributeModifiers(livingEntity, map, a);
        livingEntity.getPersistentData().putDouble("cntGEft", 0);
    }

    @Override
    public boolean isDurationEffectTick(int a, int b) {return true;}

    @Override
    public String getDescriptionId() {return "jujutsufin.effect.gravity";}
}

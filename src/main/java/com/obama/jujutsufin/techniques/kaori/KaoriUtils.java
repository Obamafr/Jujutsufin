package com.obama.jujutsufin.techniques.kaori;

import com.obama.jujutsufin.techniques.Skill;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.AttackContinueProcedure;
import net.mcreator.jujutsucraft.procedures.OtherDomainExpansionProcedure;
import net.mcreator.jujutsucraft.procedures.RangeAttackProcedure;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class KaoriUtils extends Skill {
    public static boolean execute(ServerLevel serverLevel, double x, double y, double z, LivingEntity livingEntity, int skill) {
        boolean found = false;
        skill %= 10200;
        switch (skill) {
            case 5: {
                found = antiGravity(serverLevel, x, y, z, livingEntity);
                break;
            }
            case 6: {
                found = selfAntiGravity(livingEntity);
                break;
            }
            case 7: {
                found = gravity(serverLevel, x, y, z, livingEntity);
                break;
            }
            case 20: {
                livingEntity.getPersistentData().putDouble("skill_domain", 18);
                OtherDomainExpansionProcedure.execute(serverLevel, x, y, z, livingEntity);
                found = true;
                break;
            }
        }
        return found;
    }

    private static boolean antiGravity(ServerLevel serverLevel, double x, double y, double z, LivingEntity livingEntity) {
        charge(livingEntity, 50, false);
        serverLevel.sendParticles(ParticleTypes.END_ROD, x, y + 1, z, 1, 4,0,4, 1);
        CompoundTag originData = livingEntity.getPersistentData();
        originData.putBoolean("antiGrav", true);
        originData.putInt("cntG", originData.getInt("cntG") + 1);
        double cntG = originData.getInt("cntG");
        if (originData.getBoolean("PRESS_Z")) {
            if (cntG % 20 == 0) {
                List<LivingEntity> livingEntities = serverLevel.getEntitiesOfClass(LivingEntity.class, new AABB(livingEntity.blockPosition()).inflate(4));
                for (LivingEntity entity : livingEntities) {
                    if (entity != livingEntity) {
                        entity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 44, 0));
                        entity.addEffect(new MobEffectInstance(SLOWNESS, 22, 4));
                    }
                }
                if (cntG != 0) {
                    livingEntity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(cap -> {
                        cap.PlayerCursePower -= 25;
                        cap.syncPlayerVariables(livingEntity);
                    });
                }
            }
            if (cntG == 100) {
                originData.putBoolean("PRESS_Z", false);
            }
        } else {
            originData.putInt("cntG", 0);
            originData.putBoolean("antiGrav", false);
        }
        return true;
    }

    private static boolean selfAntiGravity(LivingEntity livingEntity) {
        noCharge(livingEntity, 0);
        livingEntity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 100, 0));
        return true;
    }

    private static boolean gravity(ServerLevel serverLevel, double x, double y, double z, LivingEntity livingEntity) {
        charge(livingEntity, 100, false);
        serverLevel.sendParticles(ParticleTypes.REVERSE_PORTAL, x, y + 1, z, 1, 4,0,4, 1);
        CompoundTag originData = livingEntity.getPersistentData();
        originData.putInt("cntG", originData.getInt("cntG") + 1);
        int cntG = originData.getInt("cntG");
        if (originData.getBoolean("PRESS_Z")) {
            if (cntG % 20 == 0) {
                originData.putDouble("Damage", 15);
                originData.putDouble("knockback", 0);
                originData.putDouble("Range", 8);
                RangeAttackProcedure.execute(serverLevel, x, y, z, livingEntity);
                List<LivingEntity> livingEntities = serverLevel.getEntitiesOfClass(LivingEntity.class, new AABB(livingEntity.blockPosition()).inflate(4));
                for (LivingEntity entity : livingEntities) {
                    if (entity != livingEntity) {
                        entity.addEffect(new MobEffectInstance(SLOWNESS, 22, 9));
                    }
                }
                if (cntG != 0) {
                    livingEntity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(cap -> {
                        cap.PlayerCursePower -= 50;
                        cap.syncPlayerVariables(livingEntity);
                    });
                }
            }
            if (cntG == 100) {
                originData.putBoolean("PRESS_Z", false);
            }
        } else {
            originData.putInt("cntG", 0);
        }
        return true;
    }
}

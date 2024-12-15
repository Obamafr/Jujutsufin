package com.obama.jujutsufin.techniques.mechamaru;

import com.obama.jujutsufin.entity.BeamProjectile;
import com.obama.jujutsufin.entity.PigeonViola;
import com.obama.jujutsufin.init.JujutsufinEffects;
import com.obama.jujutsufin.init.JujutsufinParticles;
import com.obama.jujutsufin.techniques.Skill;
import net.mcreator.jujutsucraft.init.JujutsucraftModItems;
import net.mcreator.jujutsucraft.init.JujutsucraftModParticleTypes;
import net.mcreator.jujutsucraft.init.JujutsucraftModSounds;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.AttackSpeedProcedure;
import net.mcreator.jujutsucraft.procedures.KeyChangeTechniqueOnKeyPressedProcedure;
import net.mcreator.jujutsucraft.procedures.LogicAttackProcedure;
import net.mcreator.jujutsucraft.procedures.SetRangedAmmoProcedure;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;

import java.util.Comparator;

public class MechamaruUtils extends Skill {
    public static boolean execute(ServerLevel level, double x, double y, double z, LivingEntity livingEntity, int skill) {
        boolean found = false;
        CompoundTag tag = livingEntity.getPersistentData();
        skill %= 10300;
        switch (skill) {
            case 5: {
                livingEntity.setItemInHand(InteractionHand.MAIN_HAND, JujutsucraftModItems.SWORD_OPTION.get().getDefaultInstance());
                livingEntity.removeEffect(CURSEDTECHNIQUE);
                KeyChangeTechniqueOnKeyPressedProcedure.execute(level, x, y, z, livingEntity);
                found = true;
                break;
            }
            case 6: {
                livingEntity.hurtMarked = true;
                AttackSpeedProcedure.execute(level, x, y, z, livingEntity);
                found = true;
                break;
            }
            case 7:{
                int cntB = tag.getInt("cntB");
                cntB++;
                tag.putInt("cntB", cntB);
                if (cntB == 1) {
                    livingEntity.level().playSound(null, livingEntity, JujutsucraftModSounds.FRAME_SET.get(), SoundSource.PLAYERS, 1, 1);
                }
                tag.putBoolean("ultraShield", tag.getBoolean("PRESS_Z"));
                if (tag.getBoolean("PRESS_Z")) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 4));
                } else {
                    tag.putInt("cntB", 0);
                    livingEntity.removeEffect(MobEffects.GLOWING);
                }
                found = techniqueHold(livingEntity, tag, "Ultra Shield", 150);
                break;
            }
            case 8: {
                int cntB = tag.getInt("cntB");
                cntB++;
                tag.putInt("cntB", cntB);
                int cntA= tag.getInt("cntA");
                cntA++;
                tag.putInt("cntA", cntA);
                if (cntA % 5 == 0) {
                    livingEntity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY).ifPresent(cap -> {
                        cap.PlayerCursePowerChange -= 5;
                        cap.syncPlayerVariables(livingEntity);
                    });
                }
                livingEntity.fallDistance = 0;
                found = techniqueHold(livingEntity, tag, "Boost ON", 150);
                if (cntB >= 2) {
                    livingEntity.hurtMarked = true;
                    livingEntity.setDeltaMovement(livingEntity.getLookAngle().normalize().scale(1));
                    tag.putInt("cntB", 0);
                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(JujutsufinParticles.EXHAUST.get(), livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 5, 0, 0, 0, 0.5);
                    }
                }
                break;
            }
            case 9: {
                found = shootBeam(livingEntity, 0.5, 1, 25, 200);
                break;
            }
            case 10: {
                found = shootBeam(livingEntity, 0.25, 2, 50, 300);
                break;
            }
            case 20,21: {
                found = modeAbsolute(livingEntity);
                break;
            }
            case 22: {
                found = shootBeam(livingEntity, 0.25, 4, 100, 400);
                break;
            }
            case 23: {
                found = shootBeam(livingEntity, 0.125, 8, 250, 800);
                break;
            }
            case 24: {
                found = viola(livingEntity);
                break;
            }
        }
        return found;
    }

    private static boolean viola(LivingEntity livingEntity) {
        CompoundTag tag = livingEntity.getPersistentData();
        tag.putBoolean("PRESS_Z", true);
        techniqueHold(livingEntity, tag, "Finding Target", 200);
        if (tag.getInt("cntS") == 1) {
            livingEntity.level().playSound(null, livingEntity, JujutsucraftModSounds.FRAME_SET.get(), SoundSource.PLAYERS, 1, 1);
        }
        LivingEntity target = null;
        for (LivingEntity entity : livingEntity.level().getEntitiesOfClass(LivingEntity.class, new AABB(livingEntity.getEyePosition(), livingEntity.getEyePosition().add(livingEntity.getViewVector(1).scale(32))).inflate(1), (p) -> p != livingEntity && LogicAttackProcedure.execute(livingEntity.level(), livingEntity, p)).stream().sorted(Comparator.comparingDouble((p) -> p.distanceToSqr(livingEntity))).toList()) {
            target = entity;
        }
        if (target != null) {
            spawnViola(livingEntity, target);
        } else if (tag.getInt("cntS") == 99) {
            spawnViola(livingEntity, null);
        }
        return true;
    }

    private static void spawnViola(LivingEntity livingEntity, LivingEntity target) {
        livingEntity.removeEffect(CURSEDTECHNIQUE);
        CompoundTag tag = livingEntity.getPersistentData();
        LazyOptional<JujutsucraftModVariables.PlayerVariables> capability = livingEntity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY);
        livingEntity.addEffect(new MobEffectInstance(COOLDOWNTIME, 200));
        tag.putInt("cntS", 0);
        capability.ifPresent(cap -> {
            cap.OVERLAY1 = "";
            cap.OVERLAY2 = "";
            cap.syncPlayerVariables(livingEntity);
        });
        int timer = 5;
        String[] teleports = {" ^ ^2 ^", " ^-1 ^2 ^", " ^1 ^2 ^", " ^2 ^1.5 ^", " ^-2 ^1.5 ^"};
        for (int i = 0; i < 5; i++) {
            PigeonViola pigeonViola = new PigeonViola(livingEntity.level());
            Vec3 position = livingEntity.position();
            pigeonViola.setPos(position.x, position.y + 2, position.z);
            livingEntity.level().addFreshEntity(pigeonViola);
            SetRangedAmmoProcedure.execute(livingEntity, pigeonViola);
            pigeonViola.setOwner(livingEntity);
            pigeonViola.getPersistentData().putInt("timer", timer);
            pigeonViola.getPersistentData().putString("teleport", teleports[i]);
            pigeonViola.setTarget(target);
            pigeonViola.getEntityData().set(PigeonViola.colorType, i);
            timer += 10;
        }
    }

    private static boolean techniqueHold(LivingEntity livingEntity, CompoundTag tag, String string, int cooldown) {
        LazyOptional<JujutsucraftModVariables.PlayerVariables> capability = livingEntity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY);
        int cntS = tag.getInt("cntS");
        cntS++;
        tag.putInt("cntS", cntS);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 20 - cntS * 0.2; i++) {
            builder.append("■");
        }
        capability.ifPresent(cap -> {
            cap.OVERLAY1 = string;
            cap.OVERLAY2 = builder.toString();
            cap.syncPlayerVariables(livingEntity);
        });
        if (!tag.getBoolean("PRESS_Z") || cntS == 100) {
            livingEntity.removeEffect(CURSEDTECHNIQUE);
            livingEntity.addEffect(new MobEffectInstance(COOLDOWNTIME, cooldown));
            tag.putInt("cntS", 0);
            capability.ifPresent(cap -> {
                cap.OVERLAY1 = "";
                cap.OVERLAY2 = "";
                cap.syncPlayerVariables(livingEntity);
            });
            tag.putInt("cntA", 0);
        }
        return true;
    }

    private static boolean shootBeam(LivingEntity livingEntity, double gain, float inflation, double loss, int cooldown) {
        CompoundTag tag = livingEntity.getPersistentData();
        int cnt1 = tag.getInt("cnt1");
        cnt1++;
        tag.putInt("cnt1", cnt1);
        if (tag.getBoolean("PRESS_Z")) {
            livingEntity.addEffect(new MobEffectInstance(SLOWNESS, 5,4));
            if (cnt1 % 5 == 0) {
                if (livingEntity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY).orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCursePower > loss) {
                    tag.putDouble("cnt6", Math.min(tag.getDouble("cnt6") + gain, 5));
                    if (tag.getDouble("cnt6") == 5 - gain) {
                        livingEntity.level().playSound(null, livingEntity, SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1, 1);
                    }
                    if (tag.getDouble("cnt6") % 1 == 0 && tag.getDouble("cnt6") != 5) {
                        livingEntity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY).ifPresent(cap -> {
                            cap.PlayerCursePowerChange -= loss;
                            cap.syncPlayerVariables(livingEntity);
                        });
                    }
                }
            }
            if (livingEntity.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles((livingEntity.getPersistentData().getDouble("cnt6") == 5 ? JujutsucraftModParticleTypes.PARTICLE_CURSE_POWER_BLUE.get() : ParticleTypes.CRIT), livingEntity.getX(), livingEntity.getY() + 1, livingEntity.getZ(), 5, 0, 0, 0, 0.5);
            }
        } else {
            BeamProjectile beamProjectile = new BeamProjectile(livingEntity, livingEntity.getLookAngle().scale(2), livingEntity.level(), inflation);
            beamProjectile.getPersistentData().putDouble("cnt6", livingEntity.getPersistentData().getDouble("cnt6"));
            beamProjectile.getPersistentData().putFloat("scale", inflation);
            livingEntity.level().addFreshEntity(beamProjectile);
            livingEntity.removeEffect(CURSEDTECHNIQUE);
            livingEntity.removeEffect(SLOWNESS);
            livingEntity.removeEffect(COOLDOWNTIME);
            livingEntity.addEffect(new MobEffectInstance(COOLDOWNTIME, cooldown));
        }
        return true;
    }

    private static boolean modeAbsolute(LivingEntity livingEntity) {
        boolean Absolute = livingEntity.hasEffect(JujutsufinEffects.ABSOLUTE.get());
        CompoundTag tag = livingEntity.getPersistentData();
        int cnt3 = (int) tag.getDouble("cnt3");
        cnt3++;
        if (cnt3 == 1) {
            tag.putInt("giveBack", (int) livingEntity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY).orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerSelectCurseTechniqueCost);
        }
        if (!tag.getBoolean("PRESS_Z") || cnt3 == 21) {
            tag.putInt("cnt3", 0);
            if (livingEntity instanceof Player player) {
                player.displayClientMessage(Component.empty(), true);
            }
            livingEntity.removeEffect(CURSEDTECHNIQUE);
            if (!tag.getBoolean("PRESS_Z") && !(cnt3 >= 20)) {
                livingEntity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY).ifPresent(cap -> {
                    cap.PlayerCursePower += tag.getInt("giveBack");
                    cap.syncPlayerVariables(livingEntity);
                });
            }
            if (cnt3 == 21) {
                KeyChangeTechniqueOnKeyPressedProcedure.execute(livingEntity.level(), livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), livingEntity);
            }
            return true;
        }
        tag.putInt("cnt3", cnt3);
        int loop = (int) ((20 - tag.getInt("cnt3")) * 0.25);
        StringBuilder string = new StringBuilder(Component.translatable("jujutsu.message.long_press").getString());
        for(int i = 0; i < loop; ++i) {
            string.insert(0, "■");
            string.append("■");
        }
        if (livingEntity instanceof Player player) {
            player.displayClientMessage(Component.literal(string.toString()), true);
        }
        if (tag.getInt("cnt3") == 20) {
            tag.putBoolean("PRESS_Z", false);
            return (!Absolute) ? livingEntity.addEffect(new MobEffectInstance(JujutsufinEffects.ABSOLUTE.get(), 3600)) : livingEntity.removeEffect(JujutsufinEffects.ABSOLUTE.get());
        }
        return true;
    }
}

package com.obama.jujutsufin.techniques;

import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class Skill {
    public static final MobEffect SLOWNESS = MobEffects.MOVEMENT_SLOWDOWN;
    public static final MobEffect COOLDOWNTIME = JujutsucraftModMobEffects.COOLDOWN_TIME.get();
    public static final MobEffect CURSEDTECHNIQUE = JujutsucraftModMobEffects.CURSED_TECHNIQUE.get();

    public static void charge(LivingEntity livingEntity, int cooldownTime, boolean slowness) {
        if (livingEntity.getPersistentData().getBoolean("PRESS_Z")) {
            if (slowness) livingEntity.addEffect(new MobEffectInstance(SLOWNESS, 0, 4));
        } else {
            livingEntity.addEffect(new MobEffectInstance(COOLDOWNTIME, cooldownTime, 0));
            livingEntity.removeEffect(CURSEDTECHNIQUE);
        }
    }
    public static void noCharge(LivingEntity livingEntity, int cooldownTime) {
        livingEntity.addEffect(new MobEffectInstance(COOLDOWNTIME, cooldownTime, 0));
        livingEntity.removeEffect(CURSEDTECHNIQUE);
    }

}

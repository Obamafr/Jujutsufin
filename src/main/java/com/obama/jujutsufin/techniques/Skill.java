package com.obama.jujutsufin.techniques;

import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class Skill {
    public static final MobEffect SLOWNESS = MobEffects.MOVEMENT_SLOWDOWN;
    public static final MobEffect COOLDOWNTIME = JujutsucraftModMobEffects.COOLDOWN_TIME.get();
    public static final MobEffect CURSEDTECHNIQUE = JujutsucraftModMobEffects.CURSED_TECHNIQUE.get();

    public static void charge(Player player, int cooldownTime, boolean slowness) {
        if (player.getPersistentData().getBoolean("PRESS_Z")) {
            player.addEffect(new MobEffectInstance(SLOWNESS, (slowness ? 5 : 0), 4));
        } else {
            player.addEffect(new MobEffectInstance(COOLDOWNTIME, cooldownTime, 0));
            player.removeEffect(CURSEDTECHNIQUE);
        }
    }
    public static void noCharge(Player player, int cooldownTime) {
        player.addEffect(new MobEffectInstance(COOLDOWNTIME, cooldownTime, 0));
        player.removeEffect(CURSEDTECHNIQUE);
    }

}

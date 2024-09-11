package com.obama.jujutsufin.effects;

import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import com.obama.jujutsufin.init.JujutsufinGameRules;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;



public class BurnoutEffect extends MobEffect {
    public BurnoutEffect() {
        super(MobEffectCategory.BENEFICIAL, 16711680);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amp) {
        MobEffect CLD = JujutsucraftModMobEffects.COOLDOWN_TIME.get();
        MobEffect UST = JujutsucraftModMobEffects.UNSTABLE.get();
        MobEffectInstance COOLDOWN = livingEntity.getEffect(CLD);
        MobEffectInstance UNSTABLE = livingEntity.getEffect(UST);
        MobEffectInstance WEAKNESS = livingEntity.getEffect(MobEffects.WEAKNESS);
        boolean cost = false;
        if (COOLDOWN != null && !(WEAKNESS != null && WEAKNESS.getAmplifier() == 2)) {
            livingEntity.removeEffect(CLD);
            int duration = COOLDOWN.getDuration();
            if (duration >= 21) {
                cost = livingEntity.addEffect(new MobEffectInstance(COOLDOWN.getEffect(), duration - 20, COOLDOWN.getAmplifier()));
            }
        }
        if (UNSTABLE != null) {
            livingEntity.removeEffect(UST);
            int duration = UNSTABLE.getDuration();
            if (duration >= 21) {
                cost = livingEntity.addEffect(new MobEffectInstance(UNSTABLE.getEffect(), duration - 20, UNSTABLE.getAmplifier()));
            }
            livingEntity.getPersistentData().putDouble("BurnoutUsed", livingEntity.getPersistentData().getDouble("BurnoutUsed") + 1);
            if (Math.random() * 1000 < livingEntity.getPersistentData().getDouble("BurnoutUsed")) {
                livingEntity.getPersistentData().putDouble("BurnoutUsed", 0);
                livingEntity.addEffect(new MobEffectInstance(JujutsucraftModMobEffects.BRAIN_DAMAGE.get(), 6000, 0));
                livingEntity.removeEffect(this);
            }
        }
        if (cost) {
            double playerCost = livingEntity.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).BurnoutCost;
            double gameRuleCost = livingEntity.level().getLevelData().getGameRules().getInt(JujutsufinGameRules.BurnoutCost);
            livingEntity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(cap -> {
                cap.PlayerCursePowerChange -= (playerCost != 30 ? playerCost : gameRuleCost);
                cap.syncPlayerVariables(livingEntity);
            });
        }
    }

    @Override
    public boolean isDurationEffectTick(int a, int b) {return true;}

    @Override
    public String getDescriptionId() {return "jujutsufin.effect.burnout";}
}

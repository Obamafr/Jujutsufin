package com.obama.jujutsufin.effects;

import com.obama.jujutsufin.init.JujutsufinEffects;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class EndureEffect extends MobEffect {
    public EndureEffect() {
        super(MobEffectCategory.BENEFICIAL, 10596017);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amp) {
        MobEffectInstance Combat = livingEntity.getEffect(JujutsucraftModMobEffects.COOLDOWN_TIME_COMBAT.get());
        MobEffectInstance Exhausted = livingEntity.getEffect(JujutsufinEffects.EXHAUSTED.get());
        if (Exhausted != null && Exhausted.getAmplifier() >= 4) return;
        if (Combat != null) {
            livingEntity.removeEffect(Combat.getEffect());
            livingEntity.addEffect(new MobEffectInstance(Combat.getEffect(), Combat.getDuration() - 5, Combat.getAmplifier()));
            boolean gifted = livingEntity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY).orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCurseTechnique == -1;
            if (Math.random() < (gifted ? 0.005 : 0.01)) {
                if (Exhausted != null) {
                    livingEntity.removeEffect(Exhausted.getEffect());
                    livingEntity.addEffect(new MobEffectInstance(Exhausted.getEffect(), 600, Exhausted.getAmplifier() + 1));
                } else {
                    livingEntity.addEffect(new MobEffectInstance(JujutsufinEffects.EXHAUSTED.get(), 600, 0));
                }
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int d, int amp) {
        return d % 2.5 == 0;
    }

    @Override
    public String getDescriptionId() {return "jujutsufin.effect.endure";}
}

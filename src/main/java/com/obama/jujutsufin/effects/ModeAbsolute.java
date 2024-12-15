package com.obama.jujutsufin.effects;

import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.init.JujutsucraftModSounds;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import org.jetbrains.annotations.NotNull;

public class ModeAbsolute extends MobEffect {
    public ModeAbsolute() {
        super(MobEffectCategory.BENEFICIAL, 16777215);
    }

    @Override
    public void addAttributeModifiers(LivingEntity livingEntity, AttributeMap attributeMap, int i) {
        super.addAttributeModifiers(livingEntity, attributeMap, i);
        MobEffectInstance modeAbsolute = livingEntity.getEffect(this);
        livingEntity.getPersistentData().putBoolean("ultraShield", true);
        livingEntity.level().playSound(null, livingEntity, JujutsucraftModSounds.FRAME_SET.get(), SoundSource.PLAYERS, 1, 1);
        if (modeAbsolute != null) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, modeAbsolute.getDuration()));
        } else {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 3600));
        }
    }

    @Override
    public void removeAttributeModifiers(LivingEntity livingEntity, AttributeMap attributeMap, int i) {
        super.removeAttributeModifiers(livingEntity, attributeMap, i);
        livingEntity.getPersistentData().putBoolean("ultraShield", false);
        livingEntity.removeEffect(MobEffects.GLOWING);
        livingEntity.addEffect(new MobEffectInstance(JujutsucraftModMobEffects.UNSTABLE.get(), 2400));
    }

    @Override
    public boolean isDurationEffectTick(int i, int i1) {
        return true;
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int amp) {
        super.applyEffectTick(livingEntity, amp);
        if (livingEntity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerCursePower < 5) {
            livingEntity.removeEffect(this);
        } else {
            livingEntity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(cap -> {
                cap.PlayerCursePowerChange -= 5;
                cap.syncPlayerVariables(livingEntity);
            });
        }
    }
}

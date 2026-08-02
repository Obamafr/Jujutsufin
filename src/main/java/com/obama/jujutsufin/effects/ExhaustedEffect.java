package com.obama.jujutsufin.effects;

import com.obama.jujutsufin.JujutsufinMod;
import com.obama.jujutsufin.init.JujutsufinAttributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ExhaustedEffect extends MobEffect {
    public ExhaustedEffect() {
        super(MobEffectCategory.HARMFUL, 9672601);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "b2521326-f233-4f61-a702-02950cc9238e", -0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(JujutsufinAttributes.CURSE_ENERGY_REGEN.get(), "9ccdf38e-a7fe-4a15-b176-4e79aa4994d3", -0.1, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amp) {
        MobEffectInstance effectInstance = livingEntity.getEffect(this);
        if (effectInstance != null && amp != 0 && effectInstance.getDuration() == 1) {
            livingEntity.removeEffect(this);
            MobEffectInstance newEffectInstance = new MobEffectInstance(this, 600, amp - 1);
            livingEntity.addEffect(newEffectInstance);
        }
    }

    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return true;
    }

    @Override
    public String getDescriptionId() {return "jujutsufin.effect.exhausted";}
}

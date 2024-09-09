package com.obama.jujutsufin.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import java.util.UUID;

public class SFABuffs extends MobEffect {
    public SFABuffs(){
        super(MobEffectCategory.BENEFICIAL, 5592575);
        addAttributeModifier(Attributes.ATTACK_DAMAGE, UUID.randomUUID().toString(), 10, AttributeModifier.Operation.ADDITION);
        addAttributeModifier(Attributes.ARMOR, UUID.randomUUID().toString(), 5, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amp) {
        float newHealth = livingEntity.getHealth() + (1 / livingEntity.getMaxHealth());
        livingEntity.setHealth(newHealth);
    }

    @Override
    public boolean isDurationEffectTick(int a, int b) {return true;}

    @Override
    public String getDescriptionId() {return "jujutsufin.effect.sfabuff";}
}

package com.obama.jujutsufin.mixins;

import com.obama.jujutsufin.init.JujutsufinEffects;
import net.mcreator.jujutsucraft.potion.UnstableMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(UnstableMobEffect.class)
public class MixinUnstableEffect extends MobEffect {
    public MixinUnstableEffect() {
        super(MobEffectCategory.HARMFUL, -1);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int i) {
        if (!livingEntity.hasEffect(JujutsufinEffects.HWBCOOLDOWN.get())) {
            livingEntity.addEffect(new MobEffectInstance(JujutsufinEffects.HWBCOOLDOWN.get(), 300));
        }
    }

    @Override
    public boolean isDurationEffectTick(int a, int b) {
        return true;
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return List.of(ItemStack.EMPTY);
    }
}

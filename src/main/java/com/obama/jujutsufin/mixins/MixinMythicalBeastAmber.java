package com.obama.jujutsufin.mixins;

import com.obama.jujutsufin.init.JujutsufinGameRules;
import net.mcreator.jujutsucraft.potion.MythicalBeastAmberEffectMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(MythicalBeastAmberEffectMobEffect.class)
public abstract class MixinMythicalBeastAmber extends MobEffect {
    public MixinMythicalBeastAmber() {
        super(MobEffectCategory.NEUTRAL, -65281);
    }

    @Inject(method = "removeAttributeModifiers", at = @At("HEAD"), cancellable = true, remap = false)
    public void onRemoveAttributeModifiers(LivingEntity livingEntity, AttributeMap attributeMap, int i, CallbackInfo ci) {
        if (livingEntity.level().getLevelData().getGameRules().getBoolean(JujutsufinGameRules.MBANoDeath)) {
            super.removeAttributeModifiers(livingEntity, attributeMap, i);
            ci.cancel();
        }
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return List.of(ItemStack.EMPTY);
    }
}

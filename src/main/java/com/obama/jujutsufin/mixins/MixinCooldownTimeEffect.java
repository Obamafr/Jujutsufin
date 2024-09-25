package com.obama.jujutsufin.mixins;

import net.mcreator.jujutsucraft.potion.CooldownTimeMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(CooldownTimeMobEffect.class)
public abstract class MixinCooldownTimeEffect extends MobEffect {
    public MixinCooldownTimeEffect() {
        super(MobEffectCategory.HARMFUL, -1);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return List.of(ItemStack.EMPTY);
    }
}

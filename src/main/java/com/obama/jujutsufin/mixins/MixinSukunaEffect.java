package com.obama.jujutsufin.mixins;

import net.mcreator.jujutsucraft.potion.SukunaEffectMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(SukunaEffectMobEffect.class)
public abstract class MixinSukunaEffect extends MobEffect {
    public MixinSukunaEffect() {
        super(MobEffectCategory.NEUTRAL, -13434829);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return List.of(ItemStack.EMPTY);
    }
}

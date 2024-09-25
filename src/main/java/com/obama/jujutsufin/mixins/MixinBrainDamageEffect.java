package com.obama.jujutsufin.mixins;

import net.mcreator.jujutsucraft.potion.BrainDamageMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(BrainDamageMobEffect.class)
public class MixinBrainDamageEffect extends MobEffect {
    public MixinBrainDamageEffect() {
        super(MobEffectCategory.HARMFUL, -6750208);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return List.of(ItemStack.EMPTY);
    }
}

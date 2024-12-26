package com.obama.jujutsufin.mixins;

import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import net.mcreator.jujutsukaisengt.potion.AuthenticCursedTechniqueMobEffect;
import net.mcreator.jujutsukaisengt.potion.CursedTechnique2MobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CursedTechnique2MobEffect.class)
public class MixinGTTechnique2 extends MobEffect {
    public MixinGTTechnique2() {
        super(MobEffectCategory.BENEFICIAL, -6684673);
    }

    @Inject(method = "applyEffectTick", at = @At("HEAD"), cancellable = true)
    private void onApplyEffectTick(LivingEntity entity, int amplifier, CallbackInfo ci) {
        if (entity.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).CustomCT == 1) {
            ci.cancel();
        }
    }
}

package com.obama.jujutsufin.mixins;

import com.obama.jujutsufin.entity.BeamProjectile;
import net.mcreator.jujutsucraft.procedures.KnockbackProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = KnockbackProcedure.class)
public class MixinKnockback {

    @Inject(method = "execute", at = @At("HEAD"), cancellable = true, remap = false)
    private static void onExecute(LevelAccessor world, double x, double y, double z, Entity entity, CallbackInfo ci){
        if (entity instanceof BeamProjectile) {
            ci.cancel();
        }
    }


}

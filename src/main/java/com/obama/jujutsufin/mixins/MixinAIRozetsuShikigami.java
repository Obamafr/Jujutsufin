package com.obama.jujutsufin.mixins;

import com.obama.jujutsufin.entity.Shikigami;
import com.obama.jujutsufin.techniques.rozetsu.AIMaster;
import net.mcreator.jujutsucraft.procedures.AIShikigamiProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AIShikigamiProcedure.class)
public class MixinAIRozetsuShikigami {
    @Inject(method = "execute", at = @At("HEAD"), remap = false, cancellable = true)
    private static void onExecute(LevelAccessor world, double x, double y, double z, Entity entity, CallbackInfo ci) {
        if (entity instanceof Shikigami) {
            AIMaster.execute(world, x, y, z, entity);
            ci.cancel();
        }
    }

}

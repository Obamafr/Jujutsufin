package com.obama.jujutsufin.mixins;

import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.ChangeCursedTechniqueRightClickedInAirProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChangeCursedTechniqueRightClickedInAirProcedure.class)
public class MixinRightClickCTBook {
    @Inject(method = "execute", at = @At("HEAD"), remap = false)
    private static void onExecute(LevelAccessor world, double x, double y, double z, Entity entity, CallbackInfo ci) {
        entity.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY).ifPresent(cap -> {
            cap.PlayerProfession = 0;
            cap.syncPlayerVariables(entity);
        });
    }
}

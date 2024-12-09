package com.obama.jujutsufin.mixins;

import net.mcreator.jujutsucraft.procedures.SelectedProcedure;
import net.mcreator.jujutsucraft.procedures.WhenRespawnProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

@Mixin(SelectedProcedure.class)
public class MixinSelectedProcedure {
    @Inject(method = "execute", at = @At(value = "INVOKE", target = "Ljava/util/HashMap;containsKey(Ljava/lang/Object;)Z"), cancellable = true, remap = false)
    private static void bypassChanges(LevelAccessor world, double x, double y, double z, Entity entity, HashMap guistate, CallbackInfo ci) {
        if (entity instanceof Player player) player.closeContainer();
        WhenRespawnProcedure.execute(world, x, y, z, entity);
        ci.cancel();
    }
}

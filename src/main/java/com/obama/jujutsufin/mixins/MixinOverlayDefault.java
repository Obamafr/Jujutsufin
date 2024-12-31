package com.obama.jujutsufin.mixins;

import com.obama.jujutsufin.ClientConfig;
import net.mcreator.jujutsucraft.client.screens.OverlayDefaultOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(OverlayDefaultOverlay.class)
public class MixinOverlayDefault {
    @ModifyVariable(method = "eventHandler", at = @At(value = "STORE"), ordinal = 0, remap = false)
    private static int modifyWidth(int w) {
        return w - ClientConfig.OVERLAY_WIDTH.get() * 2;
    }

    @ModifyVariable(method = "eventHandler", at = @At(value = "STORE"), ordinal = 1, remap = false)
    private static int modifyHeight(int h) {
        return h - ClientConfig.OVERLAY_HEIGHT.get() * 2;
    }
}

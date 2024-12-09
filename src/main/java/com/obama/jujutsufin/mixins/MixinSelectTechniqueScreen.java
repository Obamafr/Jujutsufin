package com.obama.jujutsufin.mixins;

import com.obama.jujutsufin.JujutsufinMod;
import com.obama.jujutsufin.network.ServerFromMixinPacket;
import net.mcreator.jujutsucraft.client.gui.SelectTechniqueScreen;
import net.mcreator.jujutsucraft.world.inventory.SelectTechniqueMenu;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SelectTechniqueScreen.class)
public abstract class MixinSelectTechniqueScreen extends AbstractContainerScreen<SelectTechniqueMenu> {
    @Shadow(remap = false)
    private final Player entity;

    public MixinSelectTechniqueScreen(SelectTechniqueMenu selectTechniqueMenu, Inventory inventory, Component component, Player entity) {
        super(selectTechniqueMenu, inventory, component);
        this.entity = entity;
    }

    @Inject(method = "init", at = @At("TAIL"))
    public void onInit(CallbackInfo ci) {
        this.addRenderableWidget(new Button.Builder(Component.literal("JujutsuFIN"), button -> {
            JujutsufinMod.PACKETHANDLER.sendToServer(new ServerFromMixinPacket(2));
            ServerFromMixinPacket.keyPress(entity, 2);
        }).bounds(this.leftPos + 11, this.topPos + 178, 75, 20).build());
    }
}

package com.obama.jujutsufin.mixins;

import com.obama.jujutsufin.JujutsufinMod;
import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import com.obama.jujutsufin.network.ServerFromMixinPacket;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;



@Mixin(Checkbox.class)
public abstract class MixinCheckboxes {
    @Shadow
    public abstract boolean selected();

    private Component name;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void construction(int i, int i1, int i2, int i3, Component component, boolean b, CallbackInfo ci) {
        name = component;
    }

    @Inject(method = "onPress", at = @At("HEAD"))
    public void onOnPress(CallbackInfo ci) {
        boolean curseUser = name.equals(Component.translatable("gui.jujutsucraft.select_technique.curse_user"));
        boolean cursedSpirit = name.equals(Component.translatable("gui.jujutsucraft.select_technique.cursed_spirit"));
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        double profession = player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY).orElse(new JujutsucraftModVariables.PlayerVariables()).PlayerProfession;
        if (selected() && profession == -2) {
            JujutsufinMod.PACKETHANDLER.sendToServer(new ServerFromMixinPacket(cursedSpirit ? 1 : -1));
            ServerFromMixinPacket.keyPress(player, cursedSpirit ? 1 : -1);
        } else if (selected() && cursedSpirit || selected() && curseUser) {
            JujutsufinMod.PACKETHANDLER.sendToServer(new ServerFromMixinPacket(0));
            ServerFromMixinPacket.keyPress(player, 0);
        } else if (curseUser && profession == -1 || cursedSpirit && profession == 1) {
            JujutsufinMod.PACKETHANDLER.sendToServer(new ServerFromMixinPacket(-2));
            ServerFromMixinPacket.keyPress(player, -2);
        } else if (cursedSpirit) {
            JujutsufinMod.PACKETHANDLER.sendToServer(new ServerFromMixinPacket(-1));
            ServerFromMixinPacket.keyPress(player, -1);
        } else if (curseUser) {
            JujutsufinMod.PACKETHANDLER.sendToServer(new ServerFromMixinPacket(1));
            ServerFromMixinPacket.keyPress(player, 1);
        }
    }
}

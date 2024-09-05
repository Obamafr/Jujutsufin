package com.obama.jujutsufin.init;

import com.mojang.blaze3d.platform.InputConstants;
import com.obama.jujutsufin.JujutsufinMod;
import com.obama.jujutsufin.network.ServerKenjakuChangeTechPacket;
import com.obama.jujutsufin.network.ServerKenjakuOpenMenuPacket;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = JujutsufinMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class JujutsufinKeybinds {
    public static final JujutsufinKeybinds JFK = new JujutsufinKeybinds();

    private JujutsufinKeybinds() {}

    private static final String CATEGORY = "JujutsuFIN";

    public final KeyMapping KenjakuChangeTechnique = new KeyMapping(
            Component.translatable("jujutsufin.keybinds.kenjakuchangetechnique").getString(),
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_C, -1),
            CATEGORY
    )
    {
        private boolean wasDown = false;

        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            if (this.wasDown != isDown && isDown) {
                JujutsufinMod.PACKETHANDLER.sendToServer(new ServerKenjakuChangeTechPacket());
                ServerKenjakuChangeTechPacket.keyPress(Minecraft.getInstance().player);
            }
            this.wasDown = isDown;
        }
    };
    public final KeyMapping KenjakuOpenMenu = new KeyMapping(
            Component.translatable("jujutsufin.keybinds.kenjakuopenmenu").getString(),
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_MINUS, -1),
            CATEGORY
    )
    {
        private boolean wasDown = false;

        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            if (this.wasDown != isDown && isDown) {
                JujutsufinMod.PACKETHANDLER.sendToServer(new ServerKenjakuOpenMenuPacket());
                ServerKenjakuOpenMenuPacket.keyPress(Minecraft.getInstance().player);
            }
            this.wasDown = isDown;
        }
    };

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            JFK.KenjakuChangeTechnique.consumeClick();
        }
    }
}

package com.obama.jujutsufin.init;

import com.mojang.blaze3d.platform.InputConstants;
import com.obama.jujutsufin.JujutsufinMod;
import com.obama.jujutsufin.network.*;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.settings.KeyConflictContext;

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
                JujutsufinMod.PACKETHANDLER.sendToServer(new ServerOpenMenusPacket(1));
                ServerOpenMenusPacket.keyPress(Minecraft.getInstance().player, 1);
            }
            this.wasDown = isDown;
        }
    };
    public final KeyMapping AutoRCT = new KeyMapping(
            Component.translatable("jujutsufin.keybinds.autorct").getString(),
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_K, -1),
            CATEGORY
    )
    {
        private boolean wasDown = false;

        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            if (this.wasDown != isDown && isDown) {
                JujutsufinMod.PACKETHANDLER.sendToServer(new ServerPressAutoRCTPacket());
                ServerPressAutoRCTPacket.keyPress(Minecraft.getInstance().player);
            }
            this.wasDown = isDown;
        }
    };
    public final KeyMapping HollowWickerBasket = new KeyMapping(
            Component.translatable("jujutsufin.keybinds.hwbbutton").getString(),
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_V, -1),
            CATEGORY
    )
    {
        private boolean wasDown = false;

        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            if (this.wasDown != isDown && isDown) {
                JujutsufinMod.PACKETHANDLER.sendToServer(new ServerPressHWBPacket());
                ServerPressHWBPacket.keyPress(Minecraft.getInstance().player);
            }
            this.wasDown = isDown;
        }
    };
    public final KeyMapping BurnoutKey = new KeyMapping(
            Component.translatable("jujutsufin.keybinds.burnout").getString(),
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_Y, -1),
            CATEGORY
    )
    {
        private boolean wasDown = false;

        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            if (this.wasDown != isDown && isDown) {
                JujutsufinMod.PACKETHANDLER.sendToServer(new ServerPressedBurnoutPacket(true));
                ServerPressedBurnoutPacket.keyPress(Minecraft.getInstance().player, true);
            } else if (this.wasDown != isDown) {
                JujutsufinMod.PACKETHANDLER.sendToServer(new ServerPressedBurnoutPacket(false));
                ServerPressedBurnoutPacket.keyPress(Minecraft.getInstance().player, false);
            }
            this.wasDown = isDown;
        }
    };
    public final KeyMapping DomainHotkey = new KeyMapping(
            Component.translatable("jujutsufin.keybinds.domainhotkey").getString(),
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_H, -1),
            CATEGORY
    )
    {
        private boolean wasDown = false;

        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            if (this.wasDown != isDown && isDown) {
                JujutsufinMod.PACKETHANDLER.sendToServer(new ServerHotkeyButtonsPacket(1, true));
                ServerHotkeyButtonsPacket.keyPress(Minecraft.getInstance().player,1, true);
            } else if (this.wasDown != isDown) {
                JujutsufinMod.PACKETHANDLER.sendToServer(new ServerHotkeyButtonsPacket(1,false));
                ServerHotkeyButtonsPacket.keyPress(Minecraft.getInstance().player, 1, false);
            }
            this.wasDown = isDown;
        }
    };
    public final KeyMapping PassiveHotkey = new KeyMapping(
            Component.translatable("jujutsufin.keybinds.passivehotkey").getString(),
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_B, -1),
            CATEGORY
    )
    {
        private boolean wasDown = false;

        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            if (this.wasDown != isDown && isDown) {
                JujutsufinMod.PACKETHANDLER.sendToServer(new ServerHotkeyButtonsPacket(2, true));
                ServerHotkeyButtonsPacket.keyPress(Minecraft.getInstance().player,2, true);
            } else if (this.wasDown != isDown) {
                JujutsufinMod.PACKETHANDLER.sendToServer(new ServerHotkeyButtonsPacket(2,false));
                ServerHotkeyButtonsPacket.keyPress(Minecraft.getInstance().player, 2, false);
            }
            this.wasDown = isDown;
        }
    };
    public final KeyMapping VeilHotkey = new KeyMapping(
            Component.translatable("jujutsufin.keybinds.veilhotkey").getString(),
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_U, -1),
            CATEGORY
    )
    {
        private boolean wasDown = false;

        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            if (this.wasDown != isDown && isDown) {
                JujutsufinMod.PACKETHANDLER.sendToServer(new ServerHotkeyButtonsPacket(3, true));
                ServerHotkeyButtonsPacket.keyPress(Minecraft.getInstance().player,3, true);
            } else if (this.wasDown != isDown) {
                JujutsufinMod.PACKETHANDLER.sendToServer(new ServerHotkeyButtonsPacket(3,false));
                ServerHotkeyButtonsPacket.keyPress(Minecraft.getInstance().player, 3, false);
            }
            this.wasDown = isDown;
        }
    };
    public final KeyMapping VeilSettings = new KeyMapping(
            Component.translatable("jujutsufin.keybinds.veilsettings").getString(),
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_EQUALS, -1),
            CATEGORY
    )
    {
        private boolean wasDown = false;

        @Override
        public void setDown(boolean isDown) {
            super.setDown(isDown);
            if (this.wasDown != isDown && isDown) {
                JujutsufinMod.PACKETHANDLER.sendToServer(new ServerOpenMenusPacket(2));
                ServerOpenMenusPacket.keyPress(Minecraft.getInstance().player, 2);
            }
            this.wasDown = isDown;
        }
    };
}

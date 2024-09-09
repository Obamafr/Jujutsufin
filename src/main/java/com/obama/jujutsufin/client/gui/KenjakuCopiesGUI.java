package com.obama.jujutsufin.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.obama.jujutsufin.JujutsufinMod;
import com.obama.jujutsufin.network.ServerKenjakuGUIButtonPacket;
import com.obama.jujutsufin.techniques.kenjaku.KenjakuUtils;
import com.obama.jujutsufin.world.KenjakuCopiesMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;


import java.util.HashMap;

public class KenjakuCopiesGUI extends AbstractContainerScreen<KenjakuCopiesMenu> {
    private final ResourceLocation texture = new ResourceLocation("jujutsucraft","textures/screens/select_technique.png");
    private final Player player;

    public KenjakuCopiesGUI(KenjakuCopiesMenu kenjakuCopiesMenu, Inventory inventory, Component name) {
        super(kenjakuCopiesMenu, inventory, name);
        this.imageHeight = 76;
        this.imageWidth = 150;
        this.player = inventory.player;
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float f1, int i1, int i2) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.blit(texture, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        RenderSystem.disableBlend();
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int x, int y) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
        guiGraphics.drawString(this.font, KenjakuUtils.getCurrentTechnique2(player), 30, 29, 4210752, false);
    }

    public void init() {
        super.init();
        this.addRenderableWidget(new Button.Builder(Component.literal("<"), button -> {
            JujutsufinMod.PACKETHANDLER.sendToServer(new ServerKenjakuGUIButtonPacket(-1));
            ServerKenjakuGUIButtonPacket.keyPress(Minecraft.getInstance().player, -1);
        }).bounds(this.leftPos + 5,this.topPos + 23 , 20, 20).build());
        this.addRenderableWidget(new Button.Builder(Component.literal(">"), button -> {
            JujutsufinMod.PACKETHANDLER.sendToServer(new ServerKenjakuGUIButtonPacket(1));
            ServerKenjakuGUIButtonPacket.keyPress(Minecraft.getInstance().player, 1);
        }).bounds(this.leftPos + 125,this.topPos + 23 , 20, 20).build());
        this.addRenderableWidget(new Button.Builder(Component.translatable("jujutsufin.gui.kenjakudelete"), button -> {
            JujutsufinMod.PACKETHANDLER.sendToServer(new ServerKenjakuGUIButtonPacket(0));
            ServerKenjakuGUIButtonPacket.keyPress(Minecraft.getInstance().player, 0);
        }).bounds(this.leftPos + 55,this.topPos + 48 , 40, 20).build());
    }
}

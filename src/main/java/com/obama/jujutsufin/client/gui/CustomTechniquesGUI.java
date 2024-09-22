package com.obama.jujutsufin.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.obama.jujutsufin.JujutsufinMod;
import com.obama.jujutsufin.network.ServerCustomTechniquesPacket;
import com.obama.jujutsufin.network.ServerKenjakuGUIButtonPacket;
import com.obama.jujutsufin.techniques.kenjaku.KenjakuUtils;
import com.obama.jujutsufin.world.CustomTechniquesMenu;
import com.obama.jujutsufin.world.KenjakuCopiesMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

public class CustomTechniquesGUI extends AbstractContainerScreen<CustomTechniquesMenu> {
    private final ResourceLocation texture = new ResourceLocation("jujutsucraft","textures/screens/select_technique.png");
    private final Player player;

    public CustomTechniquesGUI(CustomTechniquesMenu customTechniquesMenu, Inventory inventory, Component name) {
        super(customTechniquesMenu, inventory, name);
        this.imageHeight = 170;
        this.imageWidth = 220;
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
    }

    public void init() {
        super.init();
        this.addRenderableWidget(new Button.Builder(Component.translatable("jujutsufin.gui.kenjaku"), button -> {
            JujutsufinMod.PACKETHANDLER.sendToServer(new ServerCustomTechniquesPacket(0));
            ServerCustomTechniquesPacket.keyPress(player, 0);
        }).bounds(this.leftPos + 5,this.topPos + 20 , 100, 20).build());
        this.addRenderableWidget(new Button.Builder(Component.translatable("jujutsufin.gui.utahime"), button -> {
            JujutsufinMod.PACKETHANDLER.sendToServer(new ServerCustomTechniquesPacket(1));
            ServerCustomTechniquesPacket.keyPress(player, 1);
        }).bounds(this.leftPos + 5,this.topPos + 50 , 100, 20).build());
        this.addRenderableWidget(new Button.Builder(Component.translatable("jujutsufin.gui.kaori"), button -> {
            JujutsufinMod.PACKETHANDLER.sendToServer(new ServerCustomTechniquesPacket(2));
            ServerCustomTechniquesPacket.keyPress(player, 2);
        }).bounds(this.leftPos + 5,this.topPos + 80 , 100, 20).build());
        this.addRenderableWidget(new Button.Builder(Component.translatable("jujutsufin.gui.jujutsucraft"), button -> {
            JujutsufinMod.PACKETHANDLER.sendToServer(new ServerCustomTechniquesPacket(3));
            ServerCustomTechniquesPacket.keyPress(player, 3);
        }).bounds(this.leftPos + 5,this.topPos + 140 , 100, 20).build());
        this.addRenderableWidget(new Button.Builder(Component.translatable("jujutsufin.gui.reset"), button -> {
            JujutsufinMod.PACKETHANDLER.sendToServer(new ServerCustomTechniquesPacket(4));
            ServerCustomTechniquesPacket.keyPress(player, 4);
        }).bounds(this.leftPos + 115,this.topPos + 140 , 100, 20).build());
    }
}

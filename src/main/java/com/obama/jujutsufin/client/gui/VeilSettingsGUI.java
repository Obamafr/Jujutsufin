package com.obama.jujutsufin.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.obama.jujutsufin.JujutsufinMod;
import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import com.obama.jujutsufin.network.ServerVeilSettingsButtonsPacket;
import com.obama.jujutsufin.world.VeilSettingsMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

import static com.obama.jujutsufin.techniques.veils.VeilsUtils.CTNames;
import static com.obama.jujutsufin.techniques.veils.VeilsUtils.CustomNames;

public class VeilSettingsGUI extends AbstractContainerScreen<VeilSettingsMenu> {
    private final ResourceLocation texture = new ResourceLocation("jujutsucraft","textures/screens/select_technique.png");
    private final Player player;


    public VeilSettingsGUI(VeilSettingsMenu veilSettingsMenu, Inventory inventory, Component name) {
        super(veilSettingsMenu, inventory, name);
        this.imageHeight = 170;
        this.imageWidth = 170;
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
        guiGraphics.drawString(this.font, Component.literal("Block Cursed Spirits:"), this.titleLabelX, this.titleLabelY + 20, 4210752, false);
        guiGraphics.drawString(this.font, Component.literal("Block Curse Users:"), this.titleLabelX, this.titleLabelY + 50, 4210752, false);
        guiGraphics.drawString(this.font, Component.literal("Block Jujutsu Sorcerers:"), this.titleLabelX, this.titleLabelY + 80, 4210752, false);
        guiGraphics.drawString(this.font, getVeilInfo(player, "P1"), this.titleLabelX + 136, this.titleLabelY + 20, 4210752, false);
        guiGraphics.drawString(this.font, getVeilInfo(player, "P2"), this.titleLabelX + 136, this.titleLabelY + 50, 4210752, false);
        guiGraphics.drawString(this.font, getVeilInfo(player, "P3"), this.titleLabelX + 136, this.titleLabelY + 80, 4210752, false);
        guiGraphics.drawString(this.font, getBlockedTechnique(player), this.titleLabelX + 25, this.titleLabelY + 140, 4210752, false);
    }

    public void init() {
        super.init();
        this.addRenderableWidget(new Button.Builder(Component.empty(), button -> {
            JujutsufinMod.PACKETHANDLER.sendToServer(new ServerVeilSettingsButtonsPacket(0));
            ServerVeilSettingsButtonsPacket.keyPress(player, 0);
        }).bounds(this.leftPos + 140,this.topPos + 20 , 25, 20).build());
        this.addRenderableWidget(new Button.Builder(Component.empty(), button -> {
            JujutsufinMod.PACKETHANDLER.sendToServer(new ServerVeilSettingsButtonsPacket(1));
            ServerVeilSettingsButtonsPacket.keyPress(player, 1);
        }).bounds(this.leftPos + 140,this.topPos + 50 , 25, 20).build());
        this.addRenderableWidget(new Button.Builder(Component.empty(), button -> {
            JujutsufinMod.PACKETHANDLER.sendToServer(new ServerVeilSettingsButtonsPacket(2));
            ServerVeilSettingsButtonsPacket.keyPress(player, 2);
        }).bounds(this.leftPos + 140,this.topPos + 80 , 25, 20).build());
        this.addRenderableWidget(new Button.Builder(Component.literal("<"), button -> {
            JujutsufinMod.PACKETHANDLER.sendToServer(new ServerVeilSettingsButtonsPacket(3));
            ServerVeilSettingsButtonsPacket.keyPress(player, 3);
        }).bounds(this.leftPos + 5,this.topPos + 140 , 20, 20).build());
        this.addRenderableWidget(new Button.Builder(Component.literal(">"), button -> {
            JujutsufinMod.PACKETHANDLER.sendToServer(new ServerVeilSettingsButtonsPacket(4));
            ServerVeilSettingsButtonsPacket.keyPress(player, 4);
        }).bounds(this.leftPos + 145,this.topPos + 140 , 20, 20).build());
    }

    private Component getVeilInfo(Player player, String name) {
        CompoundTag tag = player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).Veils;
        if (tag.getBoolean(name)) {
            return Component.literal("§2Yes");
        }
        return Component.literal("§4No");
    }

    private String getBlockedTechnique(Player player) {
        int index = player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).Veils.getInt("P4");
        if (index >= 100 && index - 100 < CustomNames.length) {
            index -= 100;
            return CustomNames[index] + " - " + (index + 100);
        }
        index++;
        if (index < 0 || index >= CTNames.length) return "Error - " + (index - 1);
        return CTNames[index] + " - " + (index - 1);
    }
}

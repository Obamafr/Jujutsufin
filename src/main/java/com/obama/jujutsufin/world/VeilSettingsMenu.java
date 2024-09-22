package com.obama.jujutsufin.world;


import com.obama.jujutsufin.init.JujutsufinGUI;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class VeilSettingsMenu extends AbstractContainerMenu {

    public VeilSettingsMenu(int id, Inventory inventory) {
        super(JujutsufinGUI.VEILSETTINGSMENU.get(), id);

    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.isAlive();
    }
}
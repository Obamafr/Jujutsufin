package com.obama.jujutsufin.techniques.mechamaru;

import com.obama.jujutsufin.init.JujutsufinEffects;
import com.obama.jujutsufin.techniques.Technique;
import net.mcreator.jujutsucraft.init.JujutsucraftModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Mechamaru extends Technique {
    public static boolean execute(Player player, int selected) {
        boolean found = false;
        boolean absolute = player.hasEffect(JujutsufinEffects.ABSOLUTE.get());
        switch (selected) {
            case 5: {
                if (!hasItem(player)) {
                    found = setInfo(player, selected, Component.translatable("jujutsufin.mechamaru.5").getString(), 0, true, true);
                }
                break;
            }
            case 6 : {
                found = setInfo(player, selected, Component.translatable("jujutsufin.mechamaru.6").getString(), 50, false, true);
                break;
            }
            case 7 : {
                if (!absolute) {
                    found = setInfo(player, selected, Component.translatable("jujutsufin.mechamaru.7").getString(), 250, false, false);
                }
                break;
            }
            case 8 : {
                found = setInfo(player, selected, Component.translatable("jujutsufin.mechamaru.8").getString(), 50, false, false);
                break;
            }
            case 9 : {
                if (!absolute) {
                    found = setInfo(player, selected, Component.translatable("jujutsufin.mechamaru.9").getString(), 150, false, false);
                }
                break;
            }
            case 10: {
                if (!absolute) {
                    found = setInfo(player, selected, Component.translatable("jujutsufin.mechamaru.10").getString(), 500, false, false);
                }
                break;
            }
            case 20: {
                if (canDomain(player) && !absolute) {
                    found = setInfo(player, selected, Component.translatable("jujutsufin.mechamaru.20").getString(), 1000, false, false);
                }
                break;
            }
            case 21: {
                if (absolute) {
                    found = setInfo(player, selected, Component.translatable("jujutsufin.mechamaru.21").getString(), 0, false, true);
                }
                break;
            }
            case 22: {
                if (absolute) {
                    found = setInfo(player, selected, Component.translatable("jujutsufin.mechamaru.22").getString(), 1000, false, false);
                }
                break;
            }
            case 23: {
                if (absolute) {
                    found = setInfo(player, selected, Component.translatable("jujutsufin.mechamaru.23").getString(), 2500, false, false);
                }
                break;
            }
            case 24: {
                if (absolute) {
                    found = setInfo(player, selected, Component.translatable("jujutsufin.mechamaru.24").getString(), 500, false, false);
                }
                break;
            }
            default : {
                found = switchDefault(player, selected);
                break;
            }
        }
        return found;
    }

    private static boolean hasItem(Player player) {
        for (ItemStack itemStack : player.getInventory().items) {
            if (itemStack.getItem() == JujutsucraftModItems.SWORD_OPTION.get()) {
                return true;
            }
        }
        return false;
    }
}

package com.obama.jujutsufin.techniques.kaori;

import com.obama.jujutsufin.techniques.Technique;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class Kaori extends Technique {
    public static boolean execute(Player player, int selected) {
        boolean found = false;
        switch (selected) {
            case 5: {
                found = setInfo(player, selected, Component.translatable("jujutsufin.kaori.5").getString(), 50, false, true);
                break;
            }
            case 6: {
                found = setInfo(player, selected, Component.translatable("jujutsufin.kaori.6").getString(), 50, false, false);
                break;
            }
            case 7: {
                if (canRCT(player)) {
                    found = setInfo(player, selected, Component.translatable("jujutsufin.kaori.7").getString(), 100, false, true);
                }
                break;
            }
            case 8: {
                if (canRCT(player)) {
                    found = setInfo(player, selected, Component.translatable("jujutsufin.kaori.8").getString(), 0, true, false);
                }
                break;
            }
            case 20: {
                if (canDomain(player) && !player.hasEffect(DOMAINEXPANSIONEFT)) {
                    found = setInfo(player, selected, Component.translatable("jujutsufin.kaori.20").getString(), 1250, false, false);
                }
                break;
            }
            default: {
                found = switchDefault(player, selected);
                break;
            }
        }
        return found;
    }
}

package com.obama.jujutsufin.techniques.utahime;

import com.obama.jujutsufin.techniques.Technique;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class Utahime extends Technique {
    public static boolean execute(Player player, int selected) {
        boolean found = false;
        switch (selected) {
            case 5: {
                found = setInfo(player, selected, Component.translatable("jujutsufin.utahime.5").getString(), 0, false, false);
                break;
            }
            case 20: {
                if (canDomain(player) && !player.hasEffect(DOMAINEXPANSIONEFT)) {
                    found = setInfo(player, selected, Component.translatable("jujutsufin.utahime.20").getString(), 1000, false, false);
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

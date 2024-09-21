package com.obama.jujutsufin.utils;

import com.obama.jujutsufin.JujutsufinMod;
import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import com.obama.jujutsufin.techniques.kenjaku.KenjakuUtils;
import com.obama.jujutsufin.techniques.utahime.UtahimeUtils;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = JujutsufinMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonForgeEventBus {
    @SubscribeEvent
    public static void PlayerRightClicksEntity(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        Entity target = event.getTarget();
        JujutsucraftModVariables.PlayerVariables playerVariables = player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables());
        JujutsufinPlayerCaps.PlayerCaps playerCaps = player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps());
        if (event.getHand() == player.getUsedItemHand()) {
            if (player.isShiftKeyDown()) {
                if (playerVariables.PlayerCurseTechnique2 == 100) {
                    UtahimeUtils.utahimeRightClick(player, target);
                }
                if (playerCaps.CustomCT == 1 && target instanceof LivingEntity livingTarget && livingTarget.getHealth() < livingTarget.getMaxHealth() / 8) {
                    if (livingTarget instanceof Player playerTarget) {
                        KenjakuUtils.rightClickPlayer(player, playerTarget);
                    } else {
                        KenjakuUtils.rightClickEntity(player, livingTarget);
                    }
                }
            }
        }
    }
}

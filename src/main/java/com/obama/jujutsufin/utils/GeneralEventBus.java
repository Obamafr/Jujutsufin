package com.obama.jujutsufin.utils;

import com.obama.jujutsufin.JujutsufinMod;
import com.obama.jujutsufin.techniques.utahime.UtahimeUtils;
import net.mcreator.jujutsucraft.procedures.EntityItemRightClickedOnEntityProcedure;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = JujutsufinMod.MODID)
public class GeneralEventBus {
    @SubscribeEvent
    public static void PlayerRightClicksEntity(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        Entity target = event.getTarget();
        if (event.getHand() == player.getUsedItemHand()) {
            if (player.isShiftKeyDown()) {
                UtahimeUtils.utahimeRightClick(player, target);
            }
        }
    }
}

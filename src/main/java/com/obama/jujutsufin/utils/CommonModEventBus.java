package com.obama.jujutsufin.utils;

import com.obama.jujutsufin.JujutsufinMod;
import com.obama.jujutsufin.entity.PigeonViola;
import com.obama.jujutsufin.entity.Shikigami;
import com.obama.jujutsufin.entity.VeilEntity;
import com.obama.jujutsufin.init.JujutsufinAttributes;
import com.obama.jujutsufin.init.JujutsufinEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = JujutsufinMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEventBus {
    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(JujutsufinEntities.Veil.get(), VeilEntity.createAttributes().build());
        event.put(JujutsufinEntities.Shikigami.get(), Shikigami.createAttributes().build());
        event.put(JujutsufinEntities.Viola.get(), PigeonViola.createMobAttributes().build());
    }
    @SubscribeEvent
    public static void addPlayerAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, JujutsufinAttributes.CURSE_ENERGY_REGEN.get());
    }
}

package com.obama.jujutsufin.commands;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class JujutsufinCommands {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(KenjakuCommand.build());
        event.getDispatcher().register(JujutsufinTeamCommand.build());
    }
}

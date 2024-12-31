package com.obama.jujutsufin.commands;


import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class JujutsufinClientCommand {
    @SubscribeEvent
    public static void registerCommands(RegisterClientCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(OverlayCommand.build());
    }
}

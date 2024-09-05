package com.obama.jujutsufin.network;

import com.obama.jujutsufin.JujutsufinMod;
import com.obama.jujutsufin.world.KenjakuCopiesMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ServerKenjakuOpenMenuPacket {

    public ServerKenjakuOpenMenuPacket() {}

    public ServerKenjakuOpenMenuPacket(FriendlyByteBuf buffer) {}

    public static void encoder(ServerKenjakuOpenMenuPacket packet, FriendlyByteBuf buffer) {}

    public static void handler(ServerKenjakuOpenMenuPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> keyPress(context.getSender()));
        context.setPacketHandled(true);
    }

    public static void keyPress(Player player) {
        if (player == null) return;
        Level world = player.level();
        if (world.hasChunkAt(player.blockPosition())) {
            if (player instanceof ServerPlayer serverPlayer) {
                NetworkHooks.openScreen(serverPlayer, new SimpleMenuProvider(
                        (containerId, playerInventory, Mplayer) -> new KenjakuCopiesMenu(containerId, playerInventory),
                        Component.translatable("jujutsufin.menu.kenjakucopies")
                ));
            }
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        JujutsufinMod.addPacket(ServerKenjakuOpenMenuPacket.class, ServerKenjakuOpenMenuPacket::encoder, ServerKenjakuOpenMenuPacket::new, ServerKenjakuOpenMenuPacket::handler);
    }
}

package com.obama.jujutsufin.network;

import com.obama.jujutsufin.JujutsufinMod;
import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import com.obama.jujutsufin.techniques.kenjaku.KenjakuUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ServerKenjakuChangeTechPacket {

    public ServerKenjakuChangeTechPacket() {}

    public ServerKenjakuChangeTechPacket(FriendlyByteBuf buffer) {}

    public static void encoder(ServerKenjakuChangeTechPacket packet, FriendlyByteBuf buffer) {}

    public static void handler(ServerKenjakuChangeTechPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> keyPress(context.getSender()));
        context.setPacketHandled(true);
    }

    public static void keyPress(Player player) {
        if (player == null) return;
        Level world = player.level();
        if (world.hasChunkAt(player.blockPosition())) {
            if (player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).CustomCT == 1) {
                KenjakuUtils.moveTechnique(player);
            }
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        JujutsufinMod.addPacket(ServerKenjakuChangeTechPacket.class, ServerKenjakuChangeTechPacket::encoder, ServerKenjakuChangeTechPacket::new, ServerKenjakuChangeTechPacket::handler);
    }
}

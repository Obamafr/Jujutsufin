package com.obama.jujutsufin.network;

import com.obama.jujutsufin.JujutsufinMod;
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
public class ServerKenjakuGUIButtonPacket {
    public int type;

    public ServerKenjakuGUIButtonPacket(int type) {
        this.type = type;
    }

    public ServerKenjakuGUIButtonPacket(FriendlyByteBuf buffer) {
        this.type = buffer.readInt();
    }

    public static void encoder(ServerKenjakuGUIButtonPacket packet, FriendlyByteBuf buffer) {
        buffer.writeInt(packet.type);
    }

    public static void handler(ServerKenjakuGUIButtonPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> keyPress(context.getSender(), packet.type));
        context.setPacketHandled(true);
    }

    public static void keyPress(Player player, int type) {
        if (player == null) return;
        Level world = player.level();
        if (world.hasChunkAt(player.blockPosition())) {
            if (type == 0) {
                KenjakuUtils.deleteTechnique(player);
            } else {
                KenjakuUtils.moveGUI(player, type);
            }
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        JujutsufinMod.addPacket(ServerKenjakuGUIButtonPacket.class, ServerKenjakuGUIButtonPacket::encoder, ServerKenjakuGUIButtonPacket::new, ServerKenjakuGUIButtonPacket::handler);
    }
}

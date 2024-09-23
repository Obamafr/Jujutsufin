package com.obama.jujutsufin.network;

import com.obama.jujutsufin.JujutsufinMod;
import net.minecraft.network.FriendlyByteBuf;
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

import static com.obama.jujutsufin.network.ServerOpenMenusPacket.getMenuFromType;
import static com.obama.jujutsufin.network.ServerOpenMenusPacket.getNameFromType;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ServerFromMixinPacket {
    public int type;

    public ServerFromMixinPacket(int type) {
        this.type = type;
    }

    public ServerFromMixinPacket(FriendlyByteBuf buffer) {
        this.type = buffer.readInt();
    }

    public static void encoder(ServerFromMixinPacket packet, FriendlyByteBuf buffer) {
        buffer.writeInt(packet.type);
    }

    public static void handler(ServerFromMixinPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> keyPress(context.getSender(), packet.type));
        context.setPacketHandled(true);
    }

    public static void keyPress(Player player, int type) {
        if (player == null) return;
        Level world = player.level();
        if (world.hasChunkAt(player.blockPosition()) && player instanceof ServerPlayer serverPlayer) {
            // switch for potential later cases
            switch (type) {
                case 0: {
                    NetworkHooks.openScreen(serverPlayer, new SimpleMenuProvider(
                            (id, inventory, mPlayer) -> getMenuFromType(3, id, inventory, mPlayer),
                            getNameFromType(3)
                    ));
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public static void registerPacket(FMLCommonSetupEvent event) {
        JujutsufinMod.addPacket(ServerFromMixinPacket.class, ServerFromMixinPacket::encoder, ServerFromMixinPacket::new, ServerFromMixinPacket::handler);
    }
}

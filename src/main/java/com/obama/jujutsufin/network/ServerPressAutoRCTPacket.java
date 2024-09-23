package com.obama.jujutsufin.network;

import com.obama.jujutsufin.JujutsufinMod;
import net.minecraft.advancements.Advancement;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ServerPressAutoRCTPacket {

    public ServerPressAutoRCTPacket() {}

    public ServerPressAutoRCTPacket(FriendlyByteBuf buffer) {}

    public static void encoder(ServerPressAutoRCTPacket packet, FriendlyByteBuf buffer) {}

    public static void handler(ServerPressAutoRCTPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> keyPress(context.getSender()));
        context.setPacketHandled(true);
    }

    public static void keyPress(Player player) {
        if (player == null) return;
        Level world = player.level();
        if (world.hasChunkAt(player.blockPosition()) && player instanceof ServerPlayer serverPlayer) {
            CompoundTag data = player.getPersistentData();
            Advancement rct1 = serverPlayer.server.getAdvancements().getAdvancement(new ResourceLocation("jujutsucraft:reverse_cursed_technique_1"));
            if (rct1 != null && serverPlayer.getAdvancements().getOrStartProgress(rct1).isDone()) {
                data.putBoolean("AUTORCT", !data.getBoolean("AUTORCT"));
                player.displayClientMessage(Component.literal("Auto Rct: " + (data.getBoolean("AUTORCT") ? "On" : "Off")), true);
            }
        }
    }

    @SubscribeEvent
    public static void registerPacket(FMLCommonSetupEvent event) {
        JujutsufinMod.addPacket(ServerPressAutoRCTPacket.class, ServerPressAutoRCTPacket::encoder, ServerPressAutoRCTPacket::new, ServerPressAutoRCTPacket::handler);
    }
}

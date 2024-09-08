package com.obama.jujutsufin.network;

import com.obama.jujutsufin.JujutsufinMod;
import com.obama.jujutsufin.init.JujutsufinEffects;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ServerGrantHWBPacket {

    public ServerGrantHWBPacket() {}

    public ServerGrantHWBPacket(FriendlyByteBuf buffer) {}

    public static void encoder(ServerGrantHWBPacket packet, FriendlyByteBuf buffer) {}

    public static void handler(ServerGrantHWBPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> keyPress(context.getSender()));
        context.setPacketHandled(true);
    }

    public static void keyPress(Player player) {
        if (player == null) return;
        Level world = player.level();
        if (world.hasChunkAt(player.blockPosition())) {
            if (player.hasEffect(JujutsufinEffects.HWB.get())) {
                player.removeEffect(JujutsufinEffects.HWB.get());
            } else if (!player.hasEffect(JujutsufinEffects.HWBCOOLDOWN.get())) {
                player.addEffect(new MobEffectInstance(JujutsufinEffects.HWB.get(), -1, 0));
            }
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        JujutsufinMod.addPacket(ServerGrantHWBPacket.class, ServerGrantHWBPacket::encoder, ServerGrantHWBPacket::new, ServerGrantHWBPacket::handler);
    }
}

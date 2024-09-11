package com.obama.jujutsufin.network;

import com.obama.jujutsufin.JujutsufinMod;
import com.obama.jujutsufin.init.JujutsufinEffects;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
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
public class ServerPressedBurnoutPacket {
    public boolean pressed;

    public ServerPressedBurnoutPacket(boolean pressed) {
        this.pressed = pressed;
    }

    public ServerPressedBurnoutPacket(FriendlyByteBuf buffer) {
        this.pressed = buffer.readBoolean();
    }

    public static void encoder(ServerPressedBurnoutPacket packet, FriendlyByteBuf buffer) {buffer.writeBoolean(packet.pressed);}

    public static void handler(ServerPressedBurnoutPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> keyPress(context.getSender(), packet.pressed));
        context.setPacketHandled(true);
    }

    public static void keyPress(Player player, boolean pressed) {
        if (player == null) return;
        if (player.hasEffect(JujutsucraftModMobEffects.BRAIN_DAMAGE.get())) return;
        Level world = player.level();
        if (world.hasChunkAt(player.blockPosition())) {
            if (pressed) {
                player.addEffect(new MobEffectInstance(JujutsufinEffects.BURNOUT.get(), -1, 0));
            } else {
                player.removeEffect(JujutsufinEffects.BURNOUT.get());
            }
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        JujutsufinMod.addPacket(ServerPressedBurnoutPacket.class, ServerPressedBurnoutPacket::encoder, ServerPressedBurnoutPacket::new, ServerPressedBurnoutPacket::handler);
    }
}

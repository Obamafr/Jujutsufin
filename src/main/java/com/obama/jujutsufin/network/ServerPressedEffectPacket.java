package com.obama.jujutsufin.network;

import com.obama.jujutsufin.JujutsufinMod;
import com.obama.jujutsufin.init.JujutsufinEffects;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.minecraft.advancements.Advancement;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ServerPressedEffectPacket {
    public boolean pressed;
    public int type;

    public ServerPressedEffectPacket(boolean pressed, int type) {
        this.pressed = pressed;
        this.type = type;
    }

    public ServerPressedEffectPacket(FriendlyByteBuf buffer) {
        this.pressed = buffer.readBoolean();
        this.type = buffer.readInt();
    }

    public static void encoder(ServerPressedEffectPacket packet, FriendlyByteBuf buffer) {
        buffer.writeBoolean(packet.pressed);
        buffer.writeInt(packet.type);
    }

    public static void handler(ServerPressedEffectPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> keyPress(context.getSender(), packet.pressed, packet.type));
        context.setPacketHandled(true);
    }

    public static void keyPress(Player player, boolean pressed, int type) {
        if (player == null) return;
        if (player instanceof ServerPlayer serverPlayer) {
            switch (type) {
                case 0 -> {
                    Advancement advancement = serverPlayer.server.getAdvancements().getAdvancement(new ResourceLocation("jujutsufin:burnout"));
                    MobEffect mobEffect = JujutsufinEffects.BURNOUT.get();
                    EffectApply(serverPlayer, pressed, advancement, mobEffect, player.hasEffect(JujutsucraftModMobEffects.BRAIN_DAMAGE.get()));
                }
                case 1 -> {
                    Advancement advancement = serverPlayer.server.getAdvancements().getAdvancement(new ResourceLocation("jujutsufin:endure"));
                    MobEffect mobEffect = JujutsufinEffects.ENDURE.get();
                    MobEffectInstance Exhausted = player.getEffect(JujutsufinEffects.EXHAUSTED.get());
                    EffectApply(serverPlayer, pressed, advancement, mobEffect, (Exhausted != null && Exhausted.getAmplifier() >= 4));
                }
            }
        }
    }



    private static void EffectApply(ServerPlayer serverPlayer, boolean pressed, Advancement advancement, MobEffect mobEffect, Boolean cantUse) {
        if (cantUse) {
            serverPlayer.removeEffect(mobEffect);
            serverPlayer.displayClientMessage(Component.translatable("jujutsufin.error.cantuse"), true);
            return;
        }
        if (advancement != null && serverPlayer.getAdvancements().getOrStartProgress(advancement).isDone()) {
            if (pressed) {
                serverPlayer.addEffect(new MobEffectInstance(mobEffect, -1, 0, false, true));
            } else {
                serverPlayer.removeEffect(mobEffect);
            }
        } else {
            serverPlayer.displayClientMessage(Component.translatable("jujutsufin.error.notunlocked"), true);
        }
    }

    @SubscribeEvent
    public static void registerPacket(FMLCommonSetupEvent event) {
        JujutsufinMod.addPacket(ServerPressedEffectPacket.class, ServerPressedEffectPacket::encoder, ServerPressedEffectPacket::new, ServerPressedEffectPacket::handler);
    }
}

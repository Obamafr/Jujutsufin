package com.obama.jujutsufin.network;

import com.obama.jujutsufin.JujutsufinMod;
import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.ChangeCursedTechniqueRightClickedInAirProcedure;
import net.mcreator.jujutsucraft.procedures.SelectedProcedure;
import net.minecraft.nbt.IntTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ServerCustomTechniquesPacket {
    public int type;

    public ServerCustomTechniquesPacket(int type) {
        this.type = type;
    }

    public ServerCustomTechniquesPacket(FriendlyByteBuf buffer) {
        this.type = buffer.readInt();
    }

    public static void encoder(ServerCustomTechniquesPacket packet, FriendlyByteBuf buffer) {
        buffer.writeInt(packet.type);
    }

    public static void handler(ServerCustomTechniquesPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> keyPress(context.getSender(), packet.type));
        context.setPacketHandled(true);
    }

    public static void keyPress(Player player, int type) {
        if (player == null) return;
        switch (type) {
            case 0 -> {
                player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).ifPresent(cap -> {
                    cap.CustomCT = 1;
                    cap.KenjakuCopies.add(0, IntTag.valueOf(102));
                    cap.syncPlayerCaps(player);
                });
                setTechnique(player, 102, 250);
            }
            case 1 -> setTechnique(player, 100, 200);
            case 5 -> setTechnique(player, 101, 200);
            case 2 -> setTechnique(player, 102, 200);
            case 6 -> setTechnique(player, 103, 500);
            case 3 -> ChangeCursedTechniqueRightClickedInAirProcedure.execute(player.level(), player.getX(), player.getY(), player.getZ(), player);
            case 4 -> player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).ifPresent(cap -> {
                        cap.CustomCT = 0;
                        cap.syncPlayerCaps(player); });
        }
    }

    private static void setTechnique(Player player, double technique, double former) {
        player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(cap -> {
            cap.PlayerCurseTechnique = technique;
            cap.PlayerCurseTechnique2 = technique;
            cap.syncPlayerVariables(player);
            SelectedProcedure.execute(player.level(), player.getX(), player.getY(), player.getZ(), player);
            cap.PlayerCursePowerFormer = former;
            cap.PlayerCursePowerMAX = former * cap.PlayerLevel;
            cap.syncPlayerVariables(player);
        });
    }

    @SubscribeEvent
    public static void registerPacket(FMLCommonSetupEvent event) {
        JujutsufinMod.addPacket(ServerCustomTechniquesPacket.class, ServerCustomTechniquesPacket::encoder, ServerCustomTechniquesPacket::new, ServerCustomTechniquesPacket::handler);
    }
}

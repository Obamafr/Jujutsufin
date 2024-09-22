package com.obama.jujutsufin.network;

import com.obama.jujutsufin.JujutsufinMod;
import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.ChangeCursedTechniqueRightClickedInAirProcedure;
import net.mcreator.jujutsucraft.procedures.SelectedProcedure;
import net.mcreator.jujutsucraft.world.inventory.SelectTechniqueMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static com.obama.jujutsufin.techniques.veils.VeilsUtils.veilP4;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ServerVeilSettingsButtonsPacket {
    public int type;

    public ServerVeilSettingsButtonsPacket(int type) {
        this.type = type;
    }

    public ServerVeilSettingsButtonsPacket(FriendlyByteBuf buffer) {
        this.type = buffer.readInt();
    }

    public static void encoder(ServerVeilSettingsButtonsPacket packet, FriendlyByteBuf buffer) {
        buffer.writeInt(packet.type);
    }

    public static void handler(ServerVeilSettingsButtonsPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> keyPress(context.getSender(), packet.type));
        context.setPacketHandled(true);
    }

    public static void keyPress(Player player, int type) {
        if (player == null) return;
        Level world = player.level();
        if (world.hasChunkAt(player.blockPosition())) {
            switch (type) {
                case 0: {
                    setVeils(player, "P1");
                    break;
                }
                case 1: {
                    setVeils(player, "P2");
                    break;
                }
                case 2: {
                    setVeils(player, "P3");
                    break;
                }
                case 3: {
                    veilP4(player, -1);
                    break;
                }
                case 4: {
                    veilP4(player, 1);
                    break;
                }
            }
        }
    }

    private static void setVeils(Player player, String name) {
        player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).ifPresent(cap -> {
            cap.Veils.putBoolean(name, !cap.Veils.getBoolean(name));
            cap.syncPlayerCaps(player);
        });
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        JujutsufinMod.addPacket(ServerVeilSettingsButtonsPacket.class, ServerVeilSettingsButtonsPacket::encoder, ServerVeilSettingsButtonsPacket::new, ServerVeilSettingsButtonsPacket::handler);
    }
}

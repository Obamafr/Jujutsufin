package com.obama.jujutsufin.network;

import com.obama.jujutsufin.JujutsufinMod;
import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import com.obama.jujutsufin.world.CustomTechniquesMenu;
import com.obama.jujutsufin.world.KenjakuCopiesMenu;
import com.obama.jujutsufin.world.VeilSettingsMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ServerOpenMenusPacket {
    public int type;

    public ServerOpenMenusPacket(int type) {
        this.type = type;
    }

    public ServerOpenMenusPacket(FriendlyByteBuf buffer) {
        this.type = buffer.readInt();
    }

    public static void encoder(ServerOpenMenusPacket packet, FriendlyByteBuf buffer) {
        buffer.writeInt(packet.type);
    }

    public static void handler(ServerOpenMenusPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> keyPress(context.getSender(), packet.type));
        context.setPacketHandled(true);
    }

    public static void keyPress(Player player, int type) {
        if (player == null) return;
        Level world = player.level();
        if (world.hasChunkAt(player.blockPosition())) {
            if (player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).CustomCT == 1) {
                if (player instanceof ServerPlayer serverPlayer) {
                    NetworkHooks.openScreen(serverPlayer, new SimpleMenuProvider(
                            (id, inventory, mPlayer) -> getMenuFromType(type, id, inventory, mPlayer),
                            getNameFromType(type)
                    ));
                }
            }
        }
    }

    public static Component getNameFromType(int type) {
        return switch (type) {
            case 1 -> Component.translatable("jujutsufin.menu.kenjakucopies");
            case 2 -> Component.translatable("jujutsufin.menu.veilsettings");
            case 3 -> Component.translatable("jujutsufin.menu.customtech");
            default -> Component.empty();
        };
    }

    public static AbstractContainerMenu getMenuFromType(int type, int id, Inventory inventory, Player player) {
        return switch (type) {
            case 1 -> new KenjakuCopiesMenu(id, inventory);
            case 2 -> new VeilSettingsMenu(id, inventory);
            case 3 -> new CustomTechniquesMenu(id, inventory);
            default -> new CraftingMenu(id, inventory);
        };
    }

    @SubscribeEvent
    public static void registerPacket(FMLCommonSetupEvent event) {
        JujutsufinMod.addPacket(ServerOpenMenusPacket.class, ServerOpenMenusPacket::encoder, ServerOpenMenusPacket::new, ServerOpenMenusPacket::handler);
    }
}

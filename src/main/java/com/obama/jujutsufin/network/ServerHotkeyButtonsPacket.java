package com.obama.jujutsufin.network;

import com.obama.jujutsufin.ClientConfig;
import com.obama.jujutsufin.JujutsufinMod;
import net.mcreator.jujutsucraft.init.JujutsucraftModMobEffects;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.mcreator.jujutsucraft.procedures.KeyChangeTechniqueOnKeyPressedProcedure;
import net.mcreator.jujutsucraft.procedures.KeyStartTechniqueOnKeyPressedProcedure;
import net.mcreator.jujutsucraft.procedures.KeyStartTechniqueOnKeyReleasedProcedure;
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
public class ServerHotkeyButtonsPacket {
    public int type;
    public boolean pressed;

    public ServerHotkeyButtonsPacket(int type, boolean pressed) {
        this.type = type;
        this.pressed = pressed;
    }

    public ServerHotkeyButtonsPacket(FriendlyByteBuf buffer) {
        this.type = buffer.readInt();
        this.pressed = buffer.readBoolean();
    }

    public static void encoder(ServerHotkeyButtonsPacket packet, FriendlyByteBuf buffer) {
        buffer.writeInt(packet.type);
        buffer.writeBoolean(packet.pressed);
    }

    public static void handler(ServerHotkeyButtonsPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> keyPress(context.getSender(), packet.type, packet.pressed));
        context.setPacketHandled(true);
    }

    public static void keyPress(Player player, int type, boolean pressed) {
        if (player == null) return;
        Level world = player.level();
        if (world.hasChunkAt(player.blockPosition())) {
            if (type == 1) {
                DomainHotkey(player, pressed);
            } else if (type == 2) {
                PassiveHotkey(player, pressed);
            } else if (type == 3) {
                VeilHotkey(player, pressed);
            }
        }
    }

    private static void VeilHotkey(Player player, boolean pressed) {
        if (pressed) {
            player.getPersistentData().putDouble("skill", 50000);
            player.getPersistentData().putBoolean("PRESS_Z", true);
            player.addEffect(new MobEffectInstance(JujutsucraftModMobEffects.CURSED_TECHNIQUE.get(), -1, 0));
        } else {
            player.getPersistentData().putInt("cnt_v", 0);
            KeyStartTechniqueOnKeyReleasedProcedure.execute(player);
        }
    }

    private static void DomainHotkey(Player player, boolean pressed) {
        Level level = player.level();
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        if (pressed) {
            player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(cap -> {
                cap.PlayerSelectCurseTechnique = 20;
                cap.syncPlayerVariables(player);
                KeyStartTechniqueOnKeyPressedProcedure.execute(level, x, y, z, player);
            });
        } else {
            if (ClientConfig.DomainHotkeyHold) {
                KeyStartTechniqueOnKeyReleasedProcedure.execute(player);
            }
        }
    }

    private static void PassiveHotkey(Player player, boolean pressed) {
        Level level = player.level();
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        JujutsucraftModVariables.PlayerVariables playerVariables = player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new JujutsucraftModVariables.PlayerVariables());
        double oldSelect = playerVariables.PlayerSelectCurseTechnique;
        if (pressed) {
            for (int i = 0; i < 52; i++) {
                if (playerVariables.PassiveTechnique) break;
                KeyChangeTechniqueOnKeyPressedProcedure.execute(level, x, y, z, player);
                if (playerVariables.PassiveTechnique) break;
                if (playerVariables.PlayerSelectCurseTechnique == oldSelect) return;
            }
            KeyStartTechniqueOnKeyPressedProcedure.execute(level, x, y, z, player);
            player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(cap -> {
                cap.PlayerSelectCurseTechnique = oldSelect;
                cap.noChangeTechnique = true;
                cap.syncPlayerVariables(player);
                KeyChangeTechniqueOnKeyPressedProcedure.execute(level, x, y, z, player);
            });
        } else {
            if (ClientConfig.PassiveHotkeyHold) {
                KeyStartTechniqueOnKeyReleasedProcedure.execute(player);
            }
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        JujutsufinMod.addPacket(ServerHotkeyButtonsPacket.class, ServerHotkeyButtonsPacket::encoder, ServerHotkeyButtonsPacket::new, ServerHotkeyButtonsPacket::handler);
    }
}

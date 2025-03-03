package com.obama.jujutsufin.capabilities;



import com.obama.jujutsufin.JujutsufinMod;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class JujutsufinPlayerCaps {
    public static final Capability<PlayerCaps> PLAYER_CAPS = CapabilityManager.get(new CapabilityToken<>() {
    });

    public JujutsufinPlayerCaps() {
    }

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        JujutsufinMod.addPacket(PlayerCapsSyncMessage.class, PlayerCapsSyncMessage::buffer, PlayerCapsSyncMessage::new, PlayerCapsSyncMessage::handler);
    }

    @SubscribeEvent
    public static void init(RegisterCapabilitiesEvent event) {
        event.register(PlayerCaps.class);
    }

    public static class PlayerCapsSyncMessage {
        private final PlayerCaps data;

        public PlayerCapsSyncMessage(FriendlyByteBuf buffer) {
            this.data = new PlayerCaps();
            this.data.readNBT(buffer.readNbt());
        }

        public PlayerCapsSyncMessage(PlayerCaps data) {
            this.data = data;
        }

        public static void buffer(PlayerCapsSyncMessage message, FriendlyByteBuf buffer) {
            buffer.writeNbt((CompoundTag)message.data.writeNBT());
        }

        public static void handler(PlayerCapsSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                if (!context.getDirection().getReceptionSide().isServer()) {
                    PlayerCaps variables = Minecraft.getInstance().player.getCapability(PLAYER_CAPS, null).orElse(new PlayerCaps());
                    variables.CustomCT = message.data.CustomCT;
                    variables.FriendNumber = message.data.FriendNumber;
                    variables.canHWB = message.data.canHWB;
                    variables.PlayerTeam = message.data.PlayerTeam;
                    variables.BlackFlashChance = message.data.BlackFlashChance;
                    variables.RCTCost = message.data.RCTCost;
                    variables.BurnoutCost = message.data.BurnoutCost;
                    variables.FatigueRate = message.data.FatigueRate;
                    variables.KenjakuCopies = message.data.KenjakuCopies;
                    variables.Veils = message.data.Veils;
                    variables.SixEyesMultiplier = message.data.SixEyesMultiplier;
                    variables.SukunaMultiplier = message.data.SukunaMultiplier;
                    variables.RozetsuCopies = message.data.RozetsuCopies;
                }
            });
            context.setPacketHandled(true);
        }
    }

    public static class PlayerCaps {
        public double CustomCT = 0;
        public double FriendNumber = Math.random();
        public boolean canHWB = true;
        public String PlayerTeam = "";
        public double BlackFlashChance = 998;
        public double RCTCost = 10;
        public double BurnoutCost = 30;
        public double FatigueRate = 20;
        public double SixEyesMultiplier = 1;
        public double SukunaMultiplier = 5;
        public ListTag KenjakuCopies = new ListTag();
        public ListTag RozetsuCopies = new ListTag();
        public CompoundTag Veils = new CompoundTag();

        public PlayerCaps() {
        }

        public void syncPlayerCaps(Entity entity) {
            if (entity instanceof ServerPlayer serverPlayer) {
                JujutsufinMod.PACKETHANDLER.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new PlayerCapsSyncMessage(this));
            }
        }

        public Tag writeNBT() {
            CompoundTag nbt = new CompoundTag();
            nbt.putDouble("CustomCT", this.CustomCT);
            nbt.putDouble("FriendNumber", this.FriendNumber);
            nbt.putBoolean("canHWB", this.canHWB);
            nbt.putString("PlayerTeam", this.PlayerTeam);
            nbt.putDouble("BlackFlashChance", this.BlackFlashChance);
            nbt.putDouble("RCTCost", this.RCTCost);
            nbt.putDouble("BurnoutCost", this.BurnoutCost);
            nbt.putDouble("FatigueRate", this.FatigueRate);
            nbt.put("KenjakuCopies", this.KenjakuCopies);
            nbt.put("Veils", this.Veils);
            nbt.putDouble("SixEyesMultiplier", this.SixEyesMultiplier);
            nbt.putDouble("SukunaMultiplier", this.SukunaMultiplier);
            nbt.put("RozetsuCopies", this.RozetsuCopies);
            return nbt;
        }

        public void readNBT(Tag Tag) {
            CompoundTag nbt = (CompoundTag)Tag;
            this.CustomCT = nbt.getDouble("CustomCT");
            this.FriendNumber = nbt.getDouble("FriendNumber");
            this.canHWB = nbt.getBoolean("canHWB");
            this.PlayerTeam = nbt.getString("PlayerTeam");
            this.BlackFlashChance = nbt.getDouble("BlackFlashChance");
            this.RCTCost = nbt.getDouble("RCTCost");
            this.BurnoutCost = nbt.getDouble("BurnoutCost");
            this.FatigueRate = nbt.getDouble("FatigueRate");
            this.KenjakuCopies = (nbt.get("KenjakuCopies") instanceof ListTag lt ? lt : new ListTag());
            this.Veils = nbt.getCompound("Veils");
            this.SixEyesMultiplier = nbt.getDouble("SixEyesMultiplier");
            this.SukunaMultiplier = nbt.getDouble("SukunaMultiplier");
            this.RozetsuCopies = (nbt.get("RozetsuCopies") instanceof ListTag lt ? lt : new ListTag());
        }
    }

    @Mod.EventBusSubscriber
    private static class PlayerCapsProvider implements ICapabilitySerializable<Tag> {
        private final PlayerCaps playerCaps = new PlayerCaps();
        private final LazyOptional<PlayerCaps> instance = LazyOptional.of(() -> this.playerCaps);

        private PlayerCapsProvider() {
        }

        @SubscribeEvent
        public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player && !(event.getObject() instanceof FakePlayer)) {
                event.addCapability(new ResourceLocation("jujutsufin", "player_caps"), new PlayerCapsProvider());
            }
        }

        public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
            return cap == PLAYER_CAPS ? this.instance.cast() : LazyOptional.empty();
        }

        public Tag serializeNBT() {
            return this.playerCaps.writeNBT();
        }

        public void deserializeNBT(Tag nbt) {
            this.playerCaps.readNBT(nbt);
        }
    }

    @Mod.EventBusSubscriber
    public static class EventBusVariableHandlers {
        public EventBusVariableHandlers() {
        }

        @SubscribeEvent
        public static void onPlayerLoggedInSyncPlayerCaps(PlayerEvent.PlayerLoggedInEvent event) {
            if (!event.getEntity().level().isClientSide()) {
                (event.getEntity().getCapability(PLAYER_CAPS, null).orElse(new PlayerCaps())).syncPlayerCaps(event.getEntity());
            }
        }

        @SubscribeEvent
        public static void onPlayerRespawnedSyncPlayerCaps(PlayerEvent.PlayerRespawnEvent event) {
            if (!event.getEntity().level().isClientSide()) {
                (event.getEntity().getCapability(PLAYER_CAPS, null).orElse(new PlayerCaps())).syncPlayerCaps(event.getEntity());
            }
        }

        @SubscribeEvent
        public static void onPlayerChangedDimensionSyncPlayerCaps(PlayerEvent.PlayerChangedDimensionEvent event) {
            if (!event.getEntity().level().isClientSide()) {
                (event.getEntity().getCapability(PLAYER_CAPS, null).orElse(new PlayerCaps())).syncPlayerCaps(event.getEntity());
            }
        }

        @SubscribeEvent
        public static void clonePlayer(PlayerEvent.Clone event) {
            event.getOriginal().revive();
            PlayerCaps original = event.getOriginal().getCapability(PLAYER_CAPS, null).orElse(new PlayerCaps());
            PlayerCaps clone = event.getEntity().getCapability(PLAYER_CAPS, null).orElse(new PlayerCaps());
            clone.CustomCT = original.CustomCT;
            clone.FriendNumber = original.FriendNumber;
            clone.canHWB = original.canHWB;
            clone.PlayerTeam = original.PlayerTeam;
            clone.BlackFlashChance = original.BlackFlashChance;
            clone.RCTCost = original.RCTCost;
            clone.BurnoutCost = original.BurnoutCost;
            clone.FatigueRate = original.FatigueRate;
            clone.KenjakuCopies = original.KenjakuCopies;
            clone.Veils = original.Veils;
            clone.SixEyesMultiplier = original.SixEyesMultiplier;
            clone.SukunaMultiplier = original.SukunaMultiplier;
            clone.RozetsuCopies = original.RozetsuCopies;
        }
    }
}

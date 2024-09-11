package com.obama.jujutsufin;

import com.mojang.logging.LogUtils;
import com.obama.jujutsufin.client.gui.CustomTechniquesGUI;
import com.obama.jujutsufin.client.gui.KenjakuCopiesGUI;
import com.obama.jujutsufin.client.particle.HWBParticle;
import com.obama.jujutsufin.client.particle.SFAParticle;
import com.obama.jujutsufin.init.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.slf4j.Logger;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Mod(JujutsufinMod.MODID)
public class JujutsufinMod
{
    public static final String MODID = "jujutsufin";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final SimpleChannel PACKETHANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation("jujutsufin", "main"), () -> "1", "1"::equals, "1"::equals);
    private static int ID = 0;

    public JujutsufinMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC, "jujutsufin-client.toml");
        JujutsufinGUI.REGISTER.register(modEventBus);
        JujutsufinItems.ITEMS.register(modEventBus);
        JujutsufinItems.TABS.register(modEventBus);
        JujutsufinEffects.EFFECTS.register(modEventBus);
        JujutsufinParticles.PARTICLES.register(modEventBus);
    }

    public static <T> void addPacket(Class<T> packet, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> handler) {
        PACKETHANDLER.registerMessage(ID, packet, encoder, decoder, handler);
        ID++;
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Mod Setup Successful");
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void registerKeybinds(RegisterKeyMappingsEvent event) {
            event.register(JujutsufinKeybinds.JFK.KenjakuChangeTechnique);
            event.register(JujutsufinKeybinds.JFK.KenjakuOpenMenu);
            event.register(JujutsufinKeybinds.JFK.HollowWickerBasket);
            event.register(JujutsufinKeybinds.JFK.BurnoutKey);
            event.register(JujutsufinKeybinds.JFK.DomainHotkey);
            event.register(JujutsufinKeybinds.JFK.PassiveHotkey);
        }
        @SubscribeEvent
        public static void registerParticles(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(JujutsufinParticles.HWBPARTICLE.get(), HWBParticle::provider);
            event.registerSpriteSet(JujutsufinParticles.SFAPARTICLE.get(), SFAParticle::provider);
        }
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                MenuScreens.register(JujutsufinGUI.KENJAKUCOPIESMENU.get(), KenjakuCopiesGUI::new);
                MenuScreens.register(JujutsufinGUI.CUSTOMTECHNIQUESMENU.get(), CustomTechniquesGUI::new);
            });
        }
    }
}

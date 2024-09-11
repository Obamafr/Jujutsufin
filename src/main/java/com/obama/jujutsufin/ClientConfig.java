package com.obama.jujutsufin;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = JujutsufinMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientConfig
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue DOMAIN_HOTKEY_HOLD = BUILDER
            .comment("Whether to hold or press Domain Hotkey")
            .define("holdDomainHotkey", false);

    private static final ForgeConfigSpec.BooleanValue PASSIVE_HOTKEY_HOLD = BUILDER
            .comment("Whether to hold or press Passive Hotkey")
            .define("holdPassiveHotkey", false);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean DomainHotkeyHold;
    public static boolean PassiveHotkeyHold;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        DomainHotkeyHold = DOMAIN_HOTKEY_HOLD.get();
        PassiveHotkeyHold = PASSIVE_HOTKEY_HOLD.get();
    }
}

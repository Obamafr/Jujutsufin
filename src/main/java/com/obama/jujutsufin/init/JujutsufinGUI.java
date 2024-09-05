package com.obama.jujutsufin.init;


import com.obama.jujutsufin.world.KenjakuCopiesMenu;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class JujutsufinGUI {
    public static final DeferredRegister<MenuType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.MENU_TYPES, "jujutsufin");
    public static final RegistryObject<MenuType<KenjakuCopiesMenu>> KENJAKUCOPIESMENU = REGISTER.register("kenjaku_copies", () -> new MenuType<>(KenjakuCopiesMenu::new, FeatureFlags.DEFAULT_FLAGS));
}

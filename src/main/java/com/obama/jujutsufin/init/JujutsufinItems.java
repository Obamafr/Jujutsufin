package com.obama.jujutsufin.init;

import com.obama.jujutsufin.JujutsufinMod;
import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import com.obama.jujutsufin.world.CustomTechniquesMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class JujutsufinItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, JujutsufinMod.MODID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, JujutsufinMod.MODID);

    public static final RegistryObject<Item> CursedWomb = ITEMS.register("cursedwomb", () -> new Item(new Item.Properties().stacksTo(9).rarity(Rarity.EPIC).food(new FoodProperties.Builder().alwaysEat().build())){
        @Override
        public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
            livingEntity.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).ifPresent(cap -> {
                if (cap.EatenWombs < 9) {
                    cap.EatenWombs++;
                    cap.syncPlayerCaps(livingEntity);
                }
            });
            return super.finishUsingItem(itemStack, level, livingEntity);
        }
    });

    public static final  RegistryObject<Item> CustomTechniqueChanger = ITEMS.register("customtechniquechanger", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)) {
        @Override
        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
            if (player instanceof ServerPlayer serverPlayer) {
                NetworkHooks.openScreen(serverPlayer, new SimpleMenuProvider(
                        (containerId, playerInventory, Mplayer) -> new CustomTechniquesMenu(containerId, playerInventory),
                        Component.translatable("jujutsufin.menu.customtech")
                ));
            }
            return super.use(level, player, interactionHand);
        }
    });

    public static final RegistryObject<BlockItem> VeilBlockItem = ITEMS.register("veil",
            () -> new BlockItem(JujutsufinBlocks.VeilBlock.get(),
                    new Item.Properties()
                            .rarity(Rarity.EPIC)
            )
    );

    public static final RegistryObject<CreativeModeTab> JujutsufinTabs = TABS.register("jujutsufin_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("jujutsufin.tab.jujutsufin"))
                    .icon(CustomTechniqueChanger.get()::getDefaultInstance)
                    .displayItems((displayParameters, output) -> {
                        output.accept(CustomTechniqueChanger.get());
                        output.accept(CursedWomb.get());
                    })
                    .build()
    );
}

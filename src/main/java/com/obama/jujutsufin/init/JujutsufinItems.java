package com.obama.jujutsufin.init;

import com.obama.jujutsufin.JujutsufinMod;
import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class JujutsufinItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, JujutsufinMod.MODID);

    public static final RegistryObject<Item> CURSEDWOMB = ITEMS.register("cursedwomb", () -> new Item(new Item.Properties().stacksTo(9).rarity(Rarity.EPIC).food(new FoodProperties.Builder().alwaysEat().build())){
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
}

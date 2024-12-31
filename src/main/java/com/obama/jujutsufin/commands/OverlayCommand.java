package com.obama.jujutsufin.commands;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.obama.jujutsufin.ClientConfig;
import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.player.Player;

public class OverlayCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        return Commands.literal("jjkfin_overlay")
                .then(Commands.literal("up").then(Commands.argument("number", IntegerArgumentType.integer()).executes(c -> {
                    ClientConfig.OVERLAY_HEIGHT.set(ClientConfig.OVERLAY_HEIGHT.get() + IntegerArgumentType.getInteger(c,"number"));
                    ClientConfig.SPEC.save();
                    return 1;
                }))).then(Commands.literal("down").then(Commands.argument("number", IntegerArgumentType.integer()).executes(c -> {
                    ClientConfig.OVERLAY_HEIGHT.set(ClientConfig.OVERLAY_HEIGHT.get() - IntegerArgumentType.getInteger(c,"number"));
                    ClientConfig.SPEC.save();
                    return 1;
                }))).then(Commands.literal("left").then(Commands.argument("number", IntegerArgumentType.integer()).executes(c -> {
                    ClientConfig.OVERLAY_WIDTH.set(ClientConfig.OVERLAY_WIDTH.get() + IntegerArgumentType.getInteger(c,"number"));
                    ClientConfig.SPEC.save();
                    return 1;
                }))).then(Commands.literal("right").then(Commands.argument("number", IntegerArgumentType.integer()).executes(c -> {
                    ClientConfig.OVERLAY_WIDTH.set(ClientConfig.OVERLAY_WIDTH.get() - IntegerArgumentType.getInteger(c,"number"));
                    ClientConfig.SPEC.save();
                    return 1;
                })));
    }
}

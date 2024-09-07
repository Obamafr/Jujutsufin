package com.obama.jujutsufin.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.player.Player;
import com.obama.jujutsufin.techniques.kenjaku.KenjakuUtils;

public class KenjakuCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        return Commands.literal("kenjaku")
                .requires(r -> r.hasPermission(2))
                .then(Commands.literal("add").then(Commands.argument("players", EntityArgument.players()).then(Commands.argument("number", IntegerArgumentType.integer()).executes(c -> {
                    for (Player player : EntityArgument.getPlayers(c, "players")) {
                        KenjakuUtils.addTechnique(player, IntegerArgumentType.getInteger(c, "number"));
                    }
                    return 1;
                })))).then(Commands.literal("remove").then(Commands.argument("players", EntityArgument.players()).then(Commands.argument("number", IntegerArgumentType.integer()).executes(c -> {
                    for (Player player : EntityArgument.getPlayers(c, "players")) {
                        KenjakuUtils.removeTechnique(player, IntegerArgumentType.getInteger(c, "number"));
                    }
                    return 1;
                }))));
    }
}

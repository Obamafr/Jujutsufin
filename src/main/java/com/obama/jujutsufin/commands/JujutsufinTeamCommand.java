package com.obama.jujutsufin.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.obama.jujutsufin.utils.TeamUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;


public class JujutsufinTeamCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        return Commands.literal("finteams")
                .then(Commands.literal("create").then(Commands.argument("name", StringArgumentType.string()).executes(c -> {
                    if (c.getSource().isPlayer()) {
                        TeamUtils.createTeam(c.getSource(), c.getSource().getPlayer(), StringArgumentType.getString(c, "name"));
                    } else {
                        sendFailure(c.getSource());
                    }
                    return 1;
                }))).then(Commands.literal("join").then(Commands.argument("name", StringArgumentType.string()).executes(c -> {
                    if (c.getSource().isPlayer()) {
                        TeamUtils.joinTeam(c.getSource(), c.getSource().getPlayer(), StringArgumentType.getString(c, "name"));
                    } else {
                        sendFailure(c.getSource());
                    }
                    return 1;
                }))).then(Commands.literal("leave").executes(c -> {
                    if (c.getSource().isPlayer()) {
                        TeamUtils.leaveTeam(c.getSource(), c.getSource().getPlayer());
                    } else {
                        sendFailure(c.getSource());
                    }
                    return 1;
                })).then(Commands.literal("invite").then(Commands.argument("player", EntityArgument.player()).executes(c -> {
                    if (c.getSource().isPlayer()){
                        TeamUtils.inviteTeam(c.getSource(), c.getSource().getPlayer(), EntityArgument.getPlayer(c,"player"));
                    } else {
                        sendFailure(c.getSource());
                    }
                    return 1;
                }))).then(Commands.literal("disband").executes(c -> {
                    if (c.getSource().isPlayer()) {
                        TeamUtils.disbandTeam(c.getSource(), c.getSource().getPlayer());
                    } else {
                        sendFailure(c.getSource());
                    }
                    return 1;
                }));
    }

    public static void sendFailure(CommandSourceStack c) {
        c.sendFailure(Component.literal("Command can't be executed from a Command Block"));
    }
}

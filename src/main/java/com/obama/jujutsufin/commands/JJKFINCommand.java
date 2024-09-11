package com.obama.jujutsufin.commands;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.player.Player;

public class JJKFINCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        return Commands.literal("jjkfin")
                .requires(r -> r.hasPermission(2))
                .then(Commands.literal("blackflashchance").then(Commands.argument("players", EntityArgument.players()).then(Commands.argument("number", DoubleArgumentType.doubleArg()).executes(c -> {
                    for (Player player : EntityArgument.getPlayers(c, "players")) {
                        player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).ifPresent(cap -> {
                            cap.BlackFlashChance = DoubleArgumentType.getDouble(c, "number");
                            cap.syncPlayerCaps(player);
                        });
                    }
                    return 1;
                })))).then(Commands.literal("sixeyesmultiplier").then(Commands.argument("players", EntityArgument.players()).then(Commands.argument("number", DoubleArgumentType.doubleArg()).executes(c -> {
                    for (Player player : EntityArgument.getPlayers(c, "players")) {
                        player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).ifPresent(cap -> {
                            cap.SixEyesMultiplier = DoubleArgumentType.getDouble(c, "number");
                            cap.syncPlayerCaps(player);
                        });
                    }
                    return 1;
                })))).then(Commands.literal("sukunamultiplier").then(Commands.argument("players", EntityArgument.players()).then(Commands.argument("number", DoubleArgumentType.doubleArg()).executes(c -> {
                    for (Player player : EntityArgument.getPlayers(c, "players")) {
                        player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).ifPresent(cap -> {
                            cap.SukunaMultiplier = DoubleArgumentType.getDouble(c, "number");
                            cap.syncPlayerCaps(player);
                        });
                    }
                    return 1;
                })))).then(Commands.literal("rctcost").then(Commands.argument("players", EntityArgument.players()).then(Commands.argument("number", DoubleArgumentType.doubleArg()).executes(c -> {
                    for (Player player : EntityArgument.getPlayers(c, "players")) {
                        player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).ifPresent(cap -> {
                            cap.RCTCost = DoubleArgumentType.getDouble(c, "number");
                            cap.syncPlayerCaps(player);
                        });
                    }
                    return 1;
                })))).then(Commands.literal("fatiguerate").then(Commands.argument("players", EntityArgument.players()).then(Commands.argument("number", DoubleArgumentType.doubleArg()).executes(c -> {
                    for (Player player : EntityArgument.getPlayers(c, "players")) {
                        player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).ifPresent(cap -> {
                            cap.FatigueRate = DoubleArgumentType.getDouble(c, "number");
                            cap.syncPlayerCaps(player);
                        });
                    }
                    return 1;
                })))).then(Commands.literal("burnoutcost").then(Commands.argument("players", EntityArgument.players()).then(Commands.argument("number", DoubleArgumentType.doubleArg()).executes(c -> {
                    for (Player player : EntityArgument.getPlayers(c, "players")) {
                        player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).ifPresent(cap -> {
                            cap.BurnoutCost = DoubleArgumentType.getDouble(c, "number");
                            cap.syncPlayerCaps(player);
                        });
                    }
                    return 1;
                }))));
    }
}

package com.obama.jujutsufin.commands;

import com.obama.jujutsufin.capabilities.JujutsufinPlayerCaps;
import net.mcreator.jujutsucraft.network.JujutsucraftModVariables;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.List;

public class TeamUtils extends SavedData {

    private TeamUtils() {}

    public ListTag Team = new ListTag();
    public ListTag FriendNumbers = new ListTag();
    public ListTag TeamOwner = new ListTag();

    public static void createTeam(CommandSourceStack c, ServerPlayer player, String name) {
        if (player == null) return;
        TeamUtils teamUtils = get(player.serverLevel());
        ListTag Team = teamUtils.Team;
        ListTag FriendNumbers = teamUtils.FriendNumbers;
        ListTag TeamOwner = teamUtils.TeamOwner;
        if (!Team.contains(StringTag.valueOf(name))) {
            Team.add(StringTag.valueOf(name));
            double random = Math.random();
            FriendNumbers.add(DoubleTag.valueOf(random));
            TeamOwner.add(StringTag.valueOf(player.getStringUUID()));
            player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).ifPresent(cap -> {
                cap.PlayerTeam = name;
                cap.syncPlayerCaps(player);
            });
            player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(cap -> {
                cap.friend_num_keep = random;
                cap.syncPlayerVariables(player);
            });
            player.getPersistentData().putDouble("friend_num", random);
            player.sendSystemMessage(Component.literal("Created team " + name));
            teamUtils.setDirty();
        } else {
            c.sendFailure(Component.literal("Team already exists."));
        }
    }

    public static void joinTeam(CommandSourceStack c, ServerPlayer player, String name) {
        if (player == null) return;
        TeamUtils teamUtils = get(player.serverLevel());
        ListTag Team = teamUtils.Team;
        ListTag FriendNumbers = teamUtils.FriendNumbers;
        if (Team.contains(StringTag.valueOf(name))) {
            if (player.getPersistentData().getCompound("TeamInvites").getBoolean(name)) {
                leaveTeam(c, player);
                player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).ifPresent(cap -> {
                    cap.PlayerTeam = name;
                    cap.syncPlayerCaps(player);
                });
                double number = FriendNumbers.getDouble(Team.indexOf(StringTag.valueOf(name)));
                player.getCapability(JujutsucraftModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(cap -> {
                    cap.friend_num_keep = number;
                    cap.syncPlayerVariables(player);
                });
                player.getPersistentData().putDouble("friend_num", number);
                player.sendSystemMessage(Component.literal("Joined team " + name), false);
            } else {
                c.sendFailure(Component.literal("You don't have an invite to this team."));
            }
        } else {
            c.sendFailure(Component.literal("Team doesn't exist."));
        }
    }

    public static void inviteTeam(CommandSourceStack c, ServerPlayer sender, ServerPlayer receiver) {
        if (sender == null) return;
        if (sender != receiver) {
            String name = sender.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).PlayerTeam;
            if (!name.isEmpty()) {
                CompoundTag tag = receiver.getPersistentData().getCompound("TeamInvites");
                tag.putBoolean(name, true);
                receiver.server.getCommands().performPrefixedCommand(receiver.createCommandSourceStack(), "tellraw @s {\"text\":\"You have been invited to team " + name + " click here to join.\\\",\\\"clickEvent\\\":{\\\"action\\\":\\\"run_command\\\",\\\"value\\\":\\\"/finteams join " + name + "\"}}");
            } else {
                c.sendFailure(Component.literal("You don't have a team."));
            }
        } else {
            c.sendFailure(Component.literal("If you one of those big pickaxe dudes. Tag ya self!"));
        }
    }

    public static void leaveTeam(CommandSourceStack c, ServerPlayer player) {
        if (player == null) return;
        TeamUtils teamUtils = get(player.serverLevel());
        ListTag TeamOwner = teamUtils.TeamOwner;
        ListTag Team = teamUtils.Team;
        String name = player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).PlayerTeam;
        if (!name.isEmpty()) {
            if (TeamOwner.contains(StringTag.valueOf(player.getStringUUID()))) {
                List<ServerPlayer> players = player.serverLevel().players();
                String playerName = "";
                boolean found = false;
                for (ServerPlayer iterator : players) {
                    if (iterator.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS,null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).PlayerTeam.equals(name) && iterator != player) {
                        if (!found) {
                            found = true;
                            playerName = iterator.getDisplayName().getString();
                            iterator.sendSystemMessage(Component.literal("You are the new team " + name + " owner"));
                            TeamOwner.add(Team.indexOf(StringTag.valueOf(name)), StringTag.valueOf(iterator.getStringUUID()));
                        } else {
                            iterator.sendSystemMessage(Component.literal(playerName + " is the new team " + name + " owner"));
                        }
                    }
                }
                if (!found) {
                    disbandTeam(c, player);
                    return;
                }
            }
            leave(player);
            player.sendSystemMessage(Component.literal("You have left team " + name));
            teamUtils.setDirty();
        } else  {
            c.sendFailure(Component.literal("You are not on a team."));
        }
    }

    private static void leave(ServerPlayer player) {
        double random = Math.random();
        player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).ifPresent(cap -> {
            cap.PlayerTeam = "";
            cap.syncPlayerCaps(player);
        });
        player.getPersistentData().putDouble("friendnum", random);
    }

    public static void disbandTeam(CommandSourceStack c, ServerPlayer player) {
        if (player == null) return;
        TeamUtils teamUtils = get(player.serverLevel());
        ListTag Team = teamUtils.Team;
        ListTag FriendNumbers = teamUtils.FriendNumbers;
        ListTag TeamOwner = teamUtils.TeamOwner;
        if (!TeamOwner.contains(StringTag.valueOf(player.getStringUUID()))) {
            c.sendFailure(Component.literal("You are not a team owner"));
            return;
        }
        ServerLevel level = player.serverLevel();
        List<ServerPlayer> players = level.players();
        String name = player.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).PlayerTeam;
        int index = Team.indexOf(StringTag.valueOf(name));
        Team.remove(index);
        FriendNumbers.remove(index);
        TeamOwner.remove(index);
        teamUtils.setDirty();
        for (ServerPlayer iterator : players) {
            if (iterator.getCapability(JujutsufinPlayerCaps.PLAYER_CAPS, null).orElse(new JujutsufinPlayerCaps.PlayerCaps()).PlayerTeam.equals(name)) {
                iterator.sendSystemMessage(Component.literal("Team " + name + " has been disbanded"), false);
                leave(iterator);
            }
        }
    }

    public TeamUtils create() {
        return new TeamUtils();
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.put("Teams", this.Team);
        tag.put("FriendNumbers", this.FriendNumbers);
        tag.put("TeamOwner", this.TeamOwner);
        return tag;
    }

    public TeamUtils load(CompoundTag tag) {
        TeamUtils data = this.create();
        data.read(tag);
        return data;
    }

    public void read(CompoundTag tag) {
        this.Team = (tag.get("Teams") instanceof ListTag lt ? lt : new ListTag());
        this.FriendNumbers = (tag.get("FriendNumbers") instanceof ListTag lt ? lt : new ListTag());
        this.TeamOwner = (tag.get("TeamOwner") instanceof ListTag lt ? lt : new ListTag());
    }

    public static TeamUtils get(ServerLevel level) {
        TeamUtils teamUtils = new TeamUtils();
        return level.getDataStorage().computeIfAbsent(teamUtils::load, teamUtils::create, "teamfin");
    }

}

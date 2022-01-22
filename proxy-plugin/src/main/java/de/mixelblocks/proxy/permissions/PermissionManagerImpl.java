package de.mixelblocks.proxy.permissions;

import de.mixelblocks.proxy.MixelProxyPlugin;
import net.kyori.adventure.identity.Identified;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;

import java.util.SortedMap;
import java.util.UUID;

public class PermissionManagerImpl implements PermissionManager {

    private final MixelProxyPlugin plugin;

    private final LuckPerms luckPerms;

    public PermissionManagerImpl(MixelProxyPlugin plugin) {
        this.plugin = plugin;
        this.luckPerms = LuckPermsProvider.get();
    }


    @Override
    public LuckPerms getLuckPerms() {
        return luckPerms;
    }

    @Override
    public String getDefaultPlayerGroupId(Identified player) {
        return getDefaultPlayerGroupId(player.identity().uuid());
    }

    @Override
    public String getDefaultPlayerGroupId(UUID uuid) {
        try {
            return luckPerms.getUserManager().getUser(uuid).getPrimaryGroup();
        } catch(Exception except) {
            return null;
        }
    }

    @Override
    public String resolveGroupPrefix(String groupId) {
        return groupMeta(groupId).getPrefix() != null ? groupMeta(groupId).getPrefix() : "";
    }

    @Override
    public String resolvePlayerGroupPrefix(Identified player) {
        return resolvePlayerGroupPrefix(player.identity().uuid());
    }

    @Override
    public String resolvePlayerGroupPrefix(UUID uuid) {
        return resolveGroupPrefix(getDefaultPlayerGroupId(uuid));
    }

    @Override
    public boolean isHigherGroup(String group_should_be_higher, String group_should_be_lower) {
        int higher = luckPerms.getGroupManager().getGroup(group_should_be_higher).getWeight() != null
                ? luckPerms.getGroupManager().getGroup(group_should_be_higher).getWeight().getAsInt() : 0;
        int lower = luckPerms.getGroupManager().getGroup(group_should_be_lower).getWeight() != null
                ? luckPerms.getGroupManager().getGroup(group_should_be_lower).getWeight().getAsInt() : 0;
        if(higher <= lower) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isHigherPlayer(Identified player_should_be_higher, Identified player_should_be_lower) {
        return isHigherGroup(resolvePlayerGroupPrefix(player_should_be_higher), resolvePlayerGroupPrefix(player_should_be_lower));
    }

    @Override
    public CachedMetaData playerMeta(Identified player) {
        return loadUser(player).getCachedData().getMetaData();
    }

    @Override
    public CachedMetaData groupMeta(String group) {
        return getLuckPerms().getGroupManager().getGroup(group).getCachedData().getMetaData();
    }

    @Override
    public User loadUser(Identified player) {
        return getLuckPerms().getUserManager().getUser(player.identity().uuid());
    }

    @Override
    public Group loadUser(String group) {
        return getLuckPerms().getGroupManager().getGroup(group);
    }

    @Override
    public String getPrefix(Identified player) {
        String prefix = playerMeta(player).getPrefix();
        return (prefix != null) ? prefix : "";
    }

    @Override
    public String getSuffix(Identified player) {
        String suffix = playerMeta(player).getSuffix();
        return (suffix != null) ? suffix : "";
    }

    @Override
    public String getPrefixes(Identified player) {
        SortedMap<Integer, String> map = playerMeta(player).getPrefixes();
        StringBuilder prefixes = new StringBuilder();
        for (String prefix : map.values())
            prefixes.append(prefix);
        return prefixes.toString();
    }

    @Override
    public String getSuffixes(Identified player) {
        SortedMap<Integer, String> map = playerMeta(player).getSuffixes();
        StringBuilder suffixes = new StringBuilder();
        for (String prefix : map.values())
            suffixes.append(prefix);
        return suffixes.toString();
    }

}

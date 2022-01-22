package de.mixelblocks.proxy.tablist;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import de.mixelblocks.proxy.MixelProxyPlugin;
import de.mixelblocks.proxy.MixelSerializer;
import net.kyori.adventure.text.TextComponent;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class TabBuilder {

    public static TextComponent formatPlayerTab(String raw, Player player) {
        raw = raw.replace("%username%", player.getUsername());
        raw = raw.replace("%prefix%", MixelProxyPlugin.api().perms().resolvePlayerGroupPrefix(player));
        raw = raw.replace("%suffix%", MixelProxyPlugin.api().perms().getSuffix(player));
        raw = raw.replace("%server%", getCurrentServer(player));
        return MixelSerializer.unusualSectionRGB.deserialize(raw);
    }

    public static TextComponent formatCustomTab(String raw, Player player) {
        raw = raw.replace("%username%", player.getUsername());
        raw = raw.replace("%prefix%", MixelProxyPlugin.api().perms().resolvePlayerGroupPrefix(player));
        raw = raw.replace("%suffix%", MixelProxyPlugin.api().perms().getSuffix(player));
        raw = raw.replace("%server%", getCurrentServer(player));
        raw = raw.replace("%ping%", String.valueOf(player.getPing()));
        raw = raw.replace("%playercount%", String.valueOf(MixelProxyPlugin.getInstance().getServer().getPlayerCount()));
        raw = raw.replace("%localplayercount%", getServerPlayerCount(player));
        raw = raw.replace("%totalmaxplayer%", String.valueOf(MixelProxyPlugin.getInstance().getServer().getConfiguration().getShowMaxPlayers()));
        raw = raw.replace("%motd%", MixelProxyPlugin.getInstance().getServer().getConfiguration().getMotd().toString());
        raw = raw.replace("%uuid%", player.getUniqueId().toString());
        raw = raw.replace("%ip%", player.getRemoteAddress().toString());
        raw = raw.replace("%balance%", getBalance(player));

        return  MixelSerializer.unusualSectionRGB.deserialize(raw);
    }

    private static String getCurrentServer(Player player) {
        if (player.getCurrentServer().isPresent())
            return player.getCurrentServer().get().getServerInfo().getName();
        else
            return "null";
    }

    private static String getBalance(Player player) {
        if (TabListHandlerImpl.playerBalances.containsKey(player.getUsername()))
            return String.valueOf(TabListHandlerImpl.playerBalances.get(player.getUsername()));
        else
            return "null";
    }

    private static String getServerPlayerCount(Player player) {
        RegisteredServer server = null;
        if (player.getCurrentServer().isPresent())
            return String.valueOf(player.getCurrentServer().get().getServer().getPlayersConnected().size());
        else
            return "null";
    }
}

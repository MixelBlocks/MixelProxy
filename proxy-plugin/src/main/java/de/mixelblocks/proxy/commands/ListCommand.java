package de.mixelblocks.proxy.commands;

import com.velocitypowered.api.command.RawCommand;
import com.velocitypowered.api.proxy.Player;
import de.mixelblocks.proxy.MixelProxyPlugin;
import de.mixelblocks.proxy.MixelSerializer;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class ListCommand implements RawCommand {

    private MixelProxyPlugin plugin;

    public ListCommand(MixelProxyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation invocation) {
        if (!invocation.source().hasPermission("proxy.command.list")) return;

        int allPlayers = MixelProxyPlugin.getInstance().getServer().getAllPlayers().size();
        int survivalPlayers = MixelProxyPlugin.getInstance().getServer().getServer("citybuild-1").get().getPlayersConnected().size();
        int farmingPlayers = MixelProxyPlugin.getInstance().getServer().getServer("farming-1").get().getPlayersConnected().size();
        int lobbyPlayers = MixelProxyPlugin.getInstance().getServer().getServer("lobby-1").get().getPlayersConnected().size();

        String playerNames = "§r§e";
        int step = 1;
        for(Player online : MixelProxyPlugin.getInstance().getServer().getAllPlayers()) {
            playerNames += (
                    step < MixelProxyPlugin.getInstance().getServer().getAllPlayers().size()
                            ? "§e" + step  + ". §6" + online.getUsername() + "§e, "
                            : "§e" + step  + ". §6" + online.getUsername()
            );
            step++;
        }
        invocation.source().sendMessage(
                MixelSerializer.sectionRGB.deserialize(
                        "§aDerzeit sind §e" + allPlayers + " §aSpieler auf mixelblocks.de" + "\n"
                                + "§r»§aCB-1 Server: §e" + survivalPlayers + "\n"
                                + "§r»§aFarmwelt Server: §e" + farmingPlayers + "\n"
                                + "§r»§aLobby Server: §e" + lobbyPlayers +
                                (allPlayers > 0 ? "\n" + "§r»§aOnline Spieler:\n" + playerNames : "")
                )
        );
    }

}

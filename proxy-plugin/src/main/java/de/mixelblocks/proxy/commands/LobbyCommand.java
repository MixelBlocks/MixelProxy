package de.mixelblocks.proxy.commands;

import com.velocitypowered.api.command.RawCommand;
import com.velocitypowered.api.proxy.Player;
import de.mixelblocks.proxy.MixelProxyPlugin;
import de.mixelblocks.proxy.MixelSerializer;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class LobbyCommand implements RawCommand {

    private MixelProxyPlugin plugin;
    public LobbyCommand(MixelProxyPlugin plugin) { this.plugin = plugin; }

    @Override
    public void execute(Invocation invocation) {
        if(!(invocation.source() instanceof Player)) return;
        if(!invocation.source().hasPermission("proxy.command.lobby")) return;

        Player player = (Player)invocation.source();

        if(plugin.getServer().getServer("lobby-1").isPresent()) {
            if(player.getCurrentServer().equals(plugin.getServer().getServer("lobby-1").get())) {
                invocation.source().sendMessage(MixelSerializer.sectionRGB.deserialize(MixelProxyPlugin.prefix + "§cDu befindest dich bereits auf der Lobby."));
                return;
            }
            player.createConnectionRequest(plugin.getServer().getServer("lobby-1").get()).fireAndForget();
        } else invocation.source().sendMessage(MixelSerializer.sectionRGB.deserialize(MixelProxyPlugin.prefix + "§cDie Lobby ist grade nicht erreichbar."));

    }

}

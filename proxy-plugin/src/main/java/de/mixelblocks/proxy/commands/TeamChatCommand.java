package de.mixelblocks.proxy.commands;

import com.velocitypowered.api.command.RawCommand;
import com.velocitypowered.api.proxy.Player;
import de.mixelblocks.proxy.MixelProxyPlugin;
import de.mixelblocks.proxy.MixelSerializer;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class TeamChatCommand implements RawCommand {

    private MixelProxyPlugin plugin;

    public TeamChatCommand(MixelProxyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation invocation) {
        if(!(invocation.source() instanceof Player)) return;
        if (!invocation.source().hasPermission("proxy.command.teamchat")) return;
        String prefix = MixelProxyPlugin.prefix_teamchat;

        // TODO Login Logout system // send ALL messages in Team-Chat when logged in

        if (invocation.arguments().length() > 0) {
            for (Player a : MixelProxyPlugin.getInstance().getServer().getAllPlayers()) {
                if (!a.hasPermission("proxy.command.teamchat")) continue;
                a.sendMessage(
                        MixelSerializer.sectionRGB.deserialize(
                                prefix + ((Player)invocation.source()).getUsername() + " §8\u00bb§a " + invocation.arguments()
                        )
                );
            }
        } else {
            invocation.source().sendMessage(
                    MixelSerializer.sectionRGB.deserialize(
                            prefix +" §cBenutze /tc <Nachricht>"
                    )
            );
        }
    }

}

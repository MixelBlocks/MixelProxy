package de.mixelblocks.proxy.commands;

import com.velocitypowered.api.command.RawCommand;
import com.velocitypowered.api.proxy.Player;
import de.mixelblocks.proxy.MixelProxyPlugin;
import de.mixelblocks.proxy.MixelSerializer;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class MaintenanceCommand implements RawCommand {

    private MixelProxyPlugin plugin;
    public MaintenanceCommand(MixelProxyPlugin plugin) { this.plugin = plugin; }

    @Override
    public void execute(Invocation invocation) {
        if(!invocation.source().hasPermission("proxy.command.maintenance")) return;
        if (plugin.getMaintenanceConfig().get().isActive()) {
            try {
                plugin.getMaintenanceConfig().get().setActive(false);
                plugin.getMaintenanceConfig().save();
                invocation.source().sendMessage(
                        MixelSerializer.sectionRGB.deserialize(
                                MixelProxyPlugin.prefix + "Du hast die §4Wartungen §cDeaktiviert."
                        )
                );
            } catch(Exception exception) {
                exception.printStackTrace();
                invocation.source().sendMessage(
                        MixelSerializer.sectionRGB.deserialize(
                                MixelProxyPlugin.prefix + "§cERROR."
                        )
                );
            }
        } else {
            try {
                plugin.getMaintenanceConfig().get().setActive(true);
                plugin.getMaintenanceConfig().save();
                invocation.source().sendMessage(
                        MixelSerializer.sectionRGB.deserialize(
                                MixelProxyPlugin.prefix + "Du hast die §4Wartungen §aAktiviert."
                        )
                );
            } catch(Exception exception) {
                exception.printStackTrace();
                invocation.source().sendMessage(
                        MixelSerializer.sectionRGB.deserialize(
                                MixelProxyPlugin.prefix + "§cERROR."
                        )
                );
            }

            for (Player onlinePlayer : plugin.getServer().getAllPlayers()) {
                if (onlinePlayer.hasPermission("bungee.command.maintenance")) continue;
                onlinePlayer.disconnect(MixelSerializer.sectionRGB.deserialize(
                        MixelProxyPlugin.disconnectionScreenTop +
                                "§cWir befinden uns jetzt im Wartungsmodus! Bitte komme später erneut." +
                                MixelProxyPlugin.disconnectionScreenBottom
                ));
            }
        }
    }

}

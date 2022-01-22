package de.mixelblocks.proxy.listener;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.proxy.Player;
import de.mixelblocks.proxy.MixelProxy;
import de.mixelblocks.proxy.MixelProxyPlugin;
import de.mixelblocks.proxy.MixelSerializer;
import de.mixelblocks.proxy.punishments.BanType;
import de.mixelblocks.proxy.punishments.BanUnit;
import de.mixelblocks.proxy.punishments.Punishment;
import de.mixelblocks.proxy.tablist.TabListTimerHandler;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class PlayerConnectListener {

    private final MixelProxyPlugin plugin;

    private final MixelProxy api;

    public PlayerConnectListener(MixelProxyPlugin plugin) { this.plugin = plugin; this.api = plugin.getApiImplementation(); }

    @Subscribe(order = PostOrder.FIRST)
    public void onPlayerConnect(LoginEvent event) {
        if(event.getPlayer() == null) return;

        Player player = event.getPlayer();

        AtomicBoolean isBanned = new AtomicBoolean(false);
        AtomicReference<Punishment> countedPunishment = null;
        api.punishManager().getPunishments(player).forEach(punishment -> {
            if(punishment.getType() == BanType.BAN) {
                isBanned.set(punishment.expired());
                countedPunishment.set(punishment);
            }
        });

        if(isBanned.get() && countedPunishment.get() != null) {
            String reason = countedPunishment.get().getReason();
            player.disconnect(MixelSerializer.ampersandRGB.deserialize(
                    "&c----------------" + MixelProxyPlugin.PREFIX
                            + "&c----------------" + "\n" + "\n" + "&cDu wurdest vom Server Gebannt." + "\n" + "\n"
                            + "&3Grund:&e" + reason + "\n" + "\n" + "&3Verbleibende Zeit: &e"
                            + BanUnit.getRemainingTime(countedPunishment.get().getUnit(), System.currentTimeMillis(), countedPunishment.get().getExpiry()) + "\n" + "\n"
                            + "&cWenn du denkst dass dieser Bann ungerecht war, dann schaue doch auf unserem Discord &emixelblocks.de/discord &cvorbei oder sende ein Entbannungsantrag unter &ehttps://dash.mixelblocks.de/entbannung&c."
                            + "\n" + "\n" + "&c----------------" + MixelProxyPlugin.PREFIX + "&c----------------"
            ));
        }

        TabListTimerHandler.updateTab();

        if(plugin.getMaintenanceConfig().get().isActive() && !player.hasPermission("proxy.maintenance.join")) {
            player.disconnect(MixelSerializer.sectionRGB.deserialize(
                    MixelProxyPlugin.disconnectionScreenTop +
                            "§cWir befinden uns aktuell im Wartungsmodus! Bitte versuche es später erneut." +
                            "\n" +
                            "§7Schau doch auch auf unserem §9Discord mixelblocks.de/discord §7vorbei." +
                            MixelProxyPlugin.disconnectionScreenBottom
            ));
        }

    }

}

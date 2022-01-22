package de.mixelblocks.proxy.listener;

import com.google.common.io.ByteArrayDataInput;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.LegacyChannelIdentifier;
import de.mixelblocks.proxy.MixelProxyPlugin;
import de.mixelblocks.proxy.tablist.TabListHandlerImpl;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class TabListListener {

    private final MixelProxyPlugin plugin;

    public TabListListener(MixelProxyPlugin plugin) {
        this.plugin = plugin;
    }


    @Subscribe
    public void onLeave(DisconnectEvent event) {
        if (plugin.getServer().getPlayerCount() > 0) {
            for (int i = 0; i < plugin.getServer().getPlayerCount(); i++) {
                Player currentPlayerToProcess = (Player) plugin.getServer().getAllPlayers().toArray()[i];
                currentPlayerToProcess.getTabList().removeEntry(event.getPlayer().getUniqueId());
            }
        }
    }

    @Subscribe
    public void onPluginMessage(PluginMessageEvent event) {
        if (!event.getIdentifier().equals(new LegacyChannelIdentifier("GlobalTabList"))) {
            return;
        }
        event.setResult(PluginMessageEvent.ForwardResult.handled());

        if (!(event.getSource() instanceof ServerConnection)) {
            return;
        }

        ByteArrayDataInput in = event.dataAsDataStream();
        String subChannel = in.readUTF();

        if (subChannel.equals("Balance")) {
            String packet[] = in.readUTF().split(":");
            String username = packet[0];
            Double balance = Double.parseDouble(packet[1]);
            if (TabListHandlerImpl.playerBalances.containsKey(username))
                TabListHandlerImpl.playerBalances.replace(username, balance);
            else
                TabListHandlerImpl.playerBalances.put(username, balance);
        }

    }

}

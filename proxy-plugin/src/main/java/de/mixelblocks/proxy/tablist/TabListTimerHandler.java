package de.mixelblocks.proxy.tablist;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.player.TabListEntry;
import com.velocitypowered.api.util.GameProfile;
import de.mixelblocks.proxy.MixelProxyPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.UUID;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class TabListTimerHandler extends TimerTask {

    public static boolean STOP = false;

    private final MixelProxyPlugin plugin;

    public TabListTimerHandler(MixelProxyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (STOP) {
            this.cancel();
            STOP = false;
            return;
        }

        try {
            if(plugin.getServer().getAllPlayers().size() < 1) return;
            for (Player currentPlayerToProcess : plugin.getServer().getAllPlayers()) {
                if (plugin.getTabListConfig().get().getAllowedServers().contains(currentPlayerToProcess.getCurrentServer())) {
                    List<UUID> toKeep = new ArrayList<UUID>();
                    for (int i2 = 0; i2 < plugin.getServer().getPlayerCount(); i2++) {
                        Player currentPlayer = (Player) plugin.getServer().getAllPlayers().toArray()[i2];
                        TabListEntry currentEntry = TabListEntry.builder().profile(currentPlayer.getGameProfile())
                                .displayName(TabBuilder.formatPlayerTab(
                                        plugin.getTabListConfig().get().getPlayer_format(), currentPlayer))
                                .tabList(currentPlayerToProcess.getTabList()).build();
                        plugin.getApiImplementation().tabListHandler().insertIntoTabListCleanly(
                                currentPlayerToProcess.getTabList(), currentEntry, toKeep);
                    }

                    if (plugin.getTabListConfig().get().isCustomTabsEnabled()) {
                        List<String> customtabs = plugin.getTabListConfig().get().getCustomTabs();
                        for (int i3 = 0; i3 < customtabs.size(); i3++) {
                            GameProfile tabProfile = GameProfile.forOfflinePlayer("customTab" + String.valueOf(i3));
                            TabListEntry currentEntry = TabListEntry.builder().profile(tabProfile)
                                    .displayName(TabBuilder.formatCustomTab(customtabs.get(i3), currentPlayerToProcess))
                                    .tabList(currentPlayerToProcess.getTabList()).build();
                            plugin.getApiImplementation().tabListHandler().insertIntoTabListCleanly(
                                    currentPlayerToProcess.getTabList(), currentEntry, toKeep);
                        }
                    }
                    for (TabListEntry current : currentPlayerToProcess.getTabList().getEntries()) {
                        if (!toKeep.contains(current.getProfile().getId()))
                            currentPlayerToProcess.getTabList().removeEntry(current.getProfile().getId());
                    }
                    currentPlayerToProcess.getTabList().clearHeaderAndFooter();
                    currentPlayerToProcess.getTabList().setHeaderAndFooter(
                            TabBuilder.formatCustomTab(plugin.getTabListConfig().get().getHeader(), currentPlayerToProcess),
                            TabBuilder.formatCustomTab(plugin.getTabListConfig().get().getFooter(), currentPlayerToProcess)
                    );

                }
            }


        } catch(Exception e) {
        }

    }

    public MixelProxyPlugin getPlugin() {
        return plugin;
    }
}

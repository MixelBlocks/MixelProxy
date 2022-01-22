package de.mixelblocks.proxy.configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class TablistConfig {

    private long updateDelay;

    private List<String> allowedServers;

    private List<String> customTabs;

    private String player_format;

    private String header;
    private String footer;

    private boolean customTabsEnabled;

    public TablistConfig(long updateDelay, List<String> allowedServers, boolean customTabsEnabled,
                         String player_format, String header, List<String> customTabs, String footer) {
        this.updateDelay = updateDelay;
        this.allowedServers = allowedServers;
        this.customTabsEnabled = customTabsEnabled;
        this.player_format = player_format;
        this.header = header;
        this.customTabs = customTabs;
        this.footer = footer;
    }

    public long getUpdateDelay() {
        return updateDelay;
    }

    public void setUpdateDelay(long updateDelay) {
        this.updateDelay = updateDelay;
    }

    public List<String> getAllowedServers() {
        return allowedServers != null ? allowedServers : new ArrayList<>();
    }

    public void setAllowedServers(List<String> allowedServers) {
        this.allowedServers = allowedServers;
    }

    public List<String> getCustomTabs() {
        return customTabs != null ? customTabs : new ArrayList<>();
    }

    public void setCustomTabs(List<String> customTabs) {
        this.customTabs = customTabs;
    }

    public boolean isCustomTabsEnabled() {
        return customTabsEnabled;
    }

    public void setCustomTabsEnabled(boolean customTabsEnabled) {
        this.customTabsEnabled = customTabsEnabled;
    }

    public String getPlayer_format() {
        return player_format;
    }

    public void setPlayer_format(String player_format) {
        this.player_format = player_format;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }
}

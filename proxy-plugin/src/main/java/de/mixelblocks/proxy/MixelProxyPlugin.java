package de.mixelblocks.proxy;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.LegacyChannelIdentifier;
import de.mixelblocks.proxy.commands.*;
import de.mixelblocks.proxy.configuration.PluginConfig;
import de.mixelblocks.proxy.configuration.JsonConfig;
import de.mixelblocks.proxy.configuration.TablistConfig;
import de.mixelblocks.proxy.configuration.MaintenanceConfig;
import de.mixelblocks.proxy.database.MongoDatabaseHandler;
import de.mixelblocks.proxy.listener.PlayerConnectListener;
import de.mixelblocks.proxy.listener.ProxyPingListener;
import de.mixelblocks.proxy.listener.TabListListener;
import de.mixelblocks.proxy.tablist.TabListTimerHandler;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Timer;


/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
@Plugin(id = "mixelproxy", name = "MixelProxy", version = "1.0-SNAPSHOT",
        url = "https://mixelblocks.de/", description = "Proxy Util", authors = {"Lucifer#1234"},
        dependencies = {
                @Dependency(id = "luckperms")
        }
)
public final class MixelProxyPlugin {

    private static MixelProxyPlugin instance;
    public static MixelProxyPlugin getInstance() { return instance; }

    public static final String PREFIX = "§r[§aMixel§4Proxy§r]",
            prefix = PREFIX + " §c» §r",
            prefix_bans = "§8[§5BanSystem§8] §a",
            prefix_teamchat = "§8[§5TeamChat§8] §6",
            disconnectionScreenTop = "§7---------------- " + PREFIX + " §7----------------\n\n",
            disconnectionScreenBottom = "\n\n§7---------------- " + PREFIX + " §7----------------";

    private final ProxyServer server;
    private final Logger logger;

    private final File dataFolder;

    private static MixelProxy apiImplementation;

    private JsonConfig<PluginConfig> config;
    private JsonConfig<TablistConfig> tabListConfig;
    private JsonConfig<MaintenanceConfig> maintenanceConfig;

    private MongoDatabaseHandler database;

    @Inject
    public MixelProxyPlugin(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        instance = this;
        this.server = server;
        this.logger = logger;
        this.dataFolder = new File(dataDirectory.toString());
        if(!this.dataFolder.exists()) this.dataFolder.mkdirs();
    }

    @Subscribe
    public void onEnable(ProxyInitializeEvent event) {

        File configPath = new File(this.getDataFolder(), "configuration/");
        if(!configPath.exists()) configPath.mkdirs();

        this.loadConfigurations();

        database = new MongoDatabaseHandler(config.get().getDatabaseConfig().getConnectionString(),
                config.get().getDatabaseConfig().getDatabaseName());

        apiImplementation = new MixelProxyImpl(this);
        MixelProxyAPI.register(apiImplementation);

        getServer().getEventManager().register(this, new ProxyPingListener(this));
        getServer().getEventManager().register(this, new PlayerConnectListener(this));
        getServer().getEventManager().register(this, new TabListListener(this));

        getServer().getChannelRegistrar().register(new LegacyChannelIdentifier("GlobalTabList"));

        new Timer().scheduleAtFixedRate(
                new TabListTimerHandler(this),
                tabListConfig.get().getUpdateDelay(),
                tabListConfig.get().getUpdateDelay()
        );

        getServer().getCommandManager().register(
                getServer().getCommandManager().metaBuilder("tabList").aliases("tab").build(),
                new TabReloadCommand(this));
        getServer().getCommandManager().register(
                getServer().getCommandManager().metaBuilder("maintenance").aliases("wartungen", "lock").build(),
                new MaintenanceCommand(this));

        getServer().getCommandManager().register(
                getServer().getCommandManager().metaBuilder("lobby").aliases("hub").build(),
                new LobbyCommand(this));
        getServer().getCommandManager().register(
                getServer().getCommandManager().metaBuilder("citybuild-1").aliases("cb-1").build(),
                new CitybuildCommand(this));
        getServer().getCommandManager().register(
                getServer().getCommandManager().metaBuilder("farmwelt-1").aliases("farming-1", "fw-1").build(),
                new FarmweltCommand(this));

        getServer().getCommandManager().register(
                getServer().getCommandManager().metaBuilder("msg").aliases("whisper").build(),
                new MSGCommand(this));
        getServer().getCommandManager().register(
                getServer().getCommandManager().metaBuilder("reply").aliases("r").build(),
                new ReplyCommand(this));
        getServer().getCommandManager().register(
                getServer().getCommandManager().metaBuilder("list").aliases("online", "players", "on").build(),
                new ListCommand(this));
        getServer().getCommandManager().register(
                getServer().getCommandManager().metaBuilder("teamchat").aliases("tc").build(),
                new TeamChatCommand(this));

        getLogger().info("Plugin enabled...");
    }

    @Subscribe
    public void onDisable(ProxyShutdownEvent event) {
        try {
            this.config.save(true);
        } catch(Exception e) {}
        try {
            this.tabListConfig.save(true);
        } catch(Exception e) {}
        try {
            this.maintenanceConfig.save(true);
        } catch(Exception e) {}
        getLogger().info("Plugin disabled...");
    }

    private void loadConfigurations() {

        PluginConfig defaultConfig = new PluginConfig("1",
                new PluginConfig.DatabaseConfig("mongodb://user:password@localhost:27017/admin", ""),
                new PluginConfig.RedisConfig("redis://localhost:6379", "", "")
        );
        config = new JsonConfig<>(PluginConfig.class, new File(this.getDataFolder(), "config.json"));
        config.setDefault(PluginConfig.class, defaultConfig);
        try {
            config.load(true);
            config.save(false);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        ArrayList<String> customTabs = new ArrayList<>();
        customTabs.add("&3Dein ping : &e%ping%");
        customTabs.add("&3Aktueller server : &e%server%");
        TablistConfig tabListConfigDefault = new TablistConfig(1000L, new ArrayList<>(), true,
                "%prefix% > %username%", "=============== MixelBlocks.de ===============",
                customTabs, "=============== MixelBlocks.de ===============");
        tabListConfig = new JsonConfig<>(TablistConfig.class, new File(this.getDataFolder(), "configuration/tablist.json"));
        tabListConfig.setDefault(TablistConfig.class, tabListConfigDefault);
        try {
            tabListConfig.load(true);
            tabListConfig.save(false);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        MaintenanceConfig maintenanceConfigDefault = new MaintenanceConfig(false, "Wartungen am Netzwerk!");
        maintenanceConfig = new JsonConfig<>(MaintenanceConfig.class, new File(this.getDataFolder(), "configuration/maintenance.json"));
        maintenanceConfig.setDefault(MaintenanceConfig.class, maintenanceConfigDefault);
        try {
            maintenanceConfig.load(true);
            maintenanceConfig.save(false);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    public Logger getLogger() {
        return logger;
    }

    public ProxyServer getServer() {
        return server;
    }

    public File getDataFolder() {
        return dataFolder;
    }

    public static MixelProxy api() {
        return apiImplementation;
    }

    public MixelProxy getApiImplementation() {
        return apiImplementation;
    }

    public JsonConfig<PluginConfig> getConfig() {
        return config;
    }

    public JsonConfig<TablistConfig> getTabListConfig() {
        return tabListConfig;
    }

    public JsonConfig<MaintenanceConfig> getMaintenanceConfig() {
        return maintenanceConfig;
    }

    public MongoDatabaseHandler db() {
        return database;
    }
}

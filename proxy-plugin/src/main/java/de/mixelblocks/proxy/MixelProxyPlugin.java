package de.mixelblocks.proxy;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import de.mixelblocks.proxy.configuration.PluginConfig;
import de.mixelblocks.proxy.configuration.JsonConfig;
import de.mixelblocks.proxy.database.MongoDatabaseHandler;
import de.mixelblocks.proxy.listener.PlayerConnectListener;
import de.mixelblocks.proxy.listener.ProxyPingListener;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;


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
        PluginConfig defaultConfig = new PluginConfig("1",
                new PluginConfig.DatabaseConfig("mongodb://user:password@localhost:27017/admin", ""),
                new PluginConfig.RedisConfig("redis://ocalhost:6379", "", "")
        );
        config = new JsonConfig<>(PluginConfig.class, new File(this.getDataFolder(), "config.json"));
        config.setDefault(PluginConfig.class, defaultConfig);
        try {
            config.save(false);
            config.load(false);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        database = new MongoDatabaseHandler(config.get().getDatabaseConfig().getConnectionString(),
                config.get().getDatabaseConfig().getDatabaseName());

        apiImplementation = new MixelProxyImpl(this);
        MixelProxyAPI.register(apiImplementation);

        getServer().getEventManager().register(this, new ProxyPingListener(this));
        getServer().getEventManager().register(this, new PlayerConnectListener(this));

        getLogger().info("Plugin enabled...");
    }

    @Subscribe
    public void onDisable(ProxyShutdownEvent event) {
        getLogger().info("Plugin disabled...");
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

    public MongoDatabaseHandler db() {
        return database;
    }
}

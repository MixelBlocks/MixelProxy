package de.mixelblocks.proxy.commands;

import com.velocitypowered.api.command.RawCommand;
import de.mixelblocks.proxy.MixelProxyPlugin;
import de.mixelblocks.proxy.MixelSerializer;
import de.mixelblocks.proxy.tablist.TabListTimerHandler;

import java.util.Timer;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class TabReloadCommand implements RawCommand {

    private MixelProxyPlugin plugin;

    public TabReloadCommand(MixelProxyPlugin plugin) { this.plugin = plugin; }

    @Override
    public void execute(Invocation invocation) {
        if(!invocation.source().hasPermission("proxy.command.tab")) return;
        String args[] = invocation.arguments().split(" ");
        if (args.length > 0) {
            if (args[0].equals("restart")) {
                TabListTimerHandler.STOP = true;
                Timer t = new Timer();
                t.scheduleAtFixedRate(
                        new TabListTimerHandler(MixelProxyPlugin.getInstance()),
                        MixelProxyPlugin.getInstance().getTabListConfig().get().getUpdateDelay(),
                        MixelProxyPlugin.getInstance().getTabListConfig().get().getUpdateDelay()
                );
                invocation.source().sendMessage(
                        MixelSerializer.sectionRGB.deserialize("&aRestarted the global tab!")
                );
            } else if (args[0].equals("config")) {
                try {
                    MixelProxyPlugin.getInstance().getTabListConfig().load();
                    invocation.source().sendMessage(
                            MixelSerializer.sectionRGB.deserialize("&aReloaded the configuration file!")
                    );
                } catch (Exception e) {
                    invocation.source().sendMessage(
                            MixelSerializer.sectionRGB.deserialize("&aError while reloading config! Additional information in Console!")
                    );
                }
            }
        } else
            invocation.source().sendMessage(
                    MixelSerializer.sectionRGB.deserialize("&cUsage : &6/tab restart/config")
            );
    }
}

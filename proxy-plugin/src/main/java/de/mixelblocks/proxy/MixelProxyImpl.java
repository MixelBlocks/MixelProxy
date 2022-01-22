package de.mixelblocks.proxy;

import de.mixelblocks.proxy.punishments.PunishManager;
import de.mixelblocks.proxy.punishments.PunishManagerImpl;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class MixelProxyImpl implements MixelProxy {

    private final MixelProxyPlugin plugin;

    private final PunishManager punishManager;

    public MixelProxyImpl(MixelProxyPlugin plugin) {
        this.plugin = plugin;
        this.punishManager = new PunishManagerImpl(plugin);
    }

    @Override
    public PunishManager punishManager() {
        return punishManager;
    }
}

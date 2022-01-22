package de.mixelblocks.proxy;

import de.mixelblocks.proxy.permissions.PermissionManager;
import de.mixelblocks.proxy.permissions.PermissionManagerImpl;
import de.mixelblocks.proxy.punishments.PunishManager;
import de.mixelblocks.proxy.punishments.PunishManagerImpl;
import de.mixelblocks.proxy.tablist.TabListHandler;
import de.mixelblocks.proxy.tablist.TabListHandlerImpl;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class MixelProxyImpl implements MixelProxy {

    private final MixelProxyPlugin plugin;

    private final PunishManager punishManager;
    private final PermissionManager permissionManager;
    private final TabListHandler tabListHandler;

    public MixelProxyImpl(MixelProxyPlugin plugin) {
        this.plugin = plugin;
        this.punishManager = new PunishManagerImpl(plugin);
        this.permissionManager = new PermissionManagerImpl(plugin);
        this.tabListHandler = new TabListHandlerImpl();
    }

    @Override
    public PunishManager punishManager() {
        return punishManager;
    }

    @Override
    public PermissionManager perms() {
        return permissionManager;
    }

    @Override
    public TabListHandler tabListHandler() {
        return tabListHandler;
    }
}

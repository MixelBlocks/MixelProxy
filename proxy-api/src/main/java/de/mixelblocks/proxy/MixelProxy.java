package de.mixelblocks.proxy;

import de.mixelblocks.proxy.permissions.PermissionManager;
import de.mixelblocks.proxy.punishments.PunishManager;
import de.mixelblocks.proxy.tablist.TabListHandler;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public interface MixelProxy {

    /**
     * resolve the punishManager
     * @return punishManager
     */
    PunishManager punishManager();

    /**
     * resolve permissions manager
     * @return permissionManager
     */
    PermissionManager perms();

    /**
     * @return tabListHandler
     */
    TabListHandler tabListHandler();

}

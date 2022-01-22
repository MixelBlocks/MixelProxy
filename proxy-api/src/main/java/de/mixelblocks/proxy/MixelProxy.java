package de.mixelblocks.proxy;

import de.mixelblocks.proxy.punishments.PunishManager;

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

}

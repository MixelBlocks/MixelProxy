package de.mixelblocks.proxy.punishments;

import net.kyori.adventure.identity.Identified;

import java.util.List;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public interface PunishManager {

    /**
     * punish a player with a specified punishment
     * @param player
     * @param punishment
     */
    void punishPlayer(Identified player, Punishment punishment);

    /**
     * un punish a player with a specified punishment ( Gets removed if exist )
     * @param player
     * @param punishment
     */
    void unPunishPlayer(Identified player, Punishment punishment);

    /**
     * un punish a player ( removes all punishments )
     * @param player
     */
    void unPunishPlayer(Identified player);

    /**
     * get the punishments of a player
     * @param player
     */
    List<Punishment> getPunishments(Identified player);

}

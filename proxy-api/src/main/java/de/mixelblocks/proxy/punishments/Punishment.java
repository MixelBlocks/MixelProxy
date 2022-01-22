package de.mixelblocks.proxy.punishments;

import java.io.Serializable;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public interface Punishment extends Serializable {

    /**
     * returns the type of the punishment
     * @return type
     */
    BanType getType();

    /**
     * returns long timestamp when the punishment is expired ( case infinite excluded )
     * @return expiryTimestamp
     */
    long getExpiry();

    /**
     * returns if the punishment is expired ( case infinite is counted inside this )
     * @return expired
     */
    boolean expired();

    /**
     * returns the unit of the punishment
     * @return unit
     */
    BanUnit getUnit();

    /**
     * Returns the reason for the punishment ( returns an empty string if no reason specified )
     * @return reason
     */
    String getReason();

}

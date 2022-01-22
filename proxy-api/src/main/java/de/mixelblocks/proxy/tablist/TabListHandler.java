package de.mixelblocks.proxy.tablist;

import com.velocitypowered.api.proxy.player.TabList;
import com.velocitypowered.api.proxy.player.TabListEntry;

import java.util.List;
import java.util.UUID;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public interface TabListHandler {

    /**
     * Insert an entry to tabList
     * @param list the tabList to insert the entry
     * @param entry the new entry o insert
     * @param toKeep uuid list of entries to keep
     */
    void insertIntoTabListCleanly(TabList list, TabListEntry entry, List<UUID> toKeep);

}

package de.mixelblocks.proxy.tablist;

import com.velocitypowered.api.proxy.player.TabList;
import com.velocitypowered.api.proxy.player.TabListEntry;

import java.util.*;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class TabListHandlerImpl implements TabListHandler {

    public static Map<String, Double> playerBalances = new HashMap<>();

    @Override
    public void insertIntoTabListCleanly(TabList list, TabListEntry entry, List<UUID> toKeep) {
        UUID inUUID = entry.getProfile().getId();
        List<UUID> containedUUIDs = new ArrayList<>();
        Map<UUID, TabListEntry> cache = new HashMap<>();
        for (TabListEntry current : list.getEntries()) {
            containedUUIDs.add(current.getProfile().getId());
            cache.put(current.getProfile().getId(), current);
        }
        if (!containedUUIDs.contains(inUUID)) {
            list.addEntry(entry);
            toKeep.add(inUUID);
            return;
        } else {
            TabListEntry currentEntr = cache.get(inUUID);
            if (currentEntr.getDisplayNameComponent() != entry.getDisplayNameComponent()) {
                list.removeEntry(inUUID);
                list.addEntry(entry);
                toKeep.add(inUUID);
            } else
                toKeep.add(inUUID);
        }
    }

}

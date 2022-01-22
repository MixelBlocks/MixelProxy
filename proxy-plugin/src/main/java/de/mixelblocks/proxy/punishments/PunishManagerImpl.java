package de.mixelblocks.proxy.punishments;

import de.mixelblocks.proxy.MixelProxyPlugin;
import de.mixelblocks.proxy.SerializableSerializer;
import de.mixelblocks.proxy.database.MongoDatabaseHandler;
import net.kyori.adventure.identity.Identified;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class PunishManagerImpl implements PunishManager {

    private final HashMap<UUID, List<Punishment>> punishments;

    private final MixelProxyPlugin plugin;

    private final MongoDatabaseHandler db;

    public PunishManagerImpl(MixelProxyPlugin plugin) {
        this.plugin = plugin;
        this.punishments = new HashMap<>();
        this.db = plugin.db();
    }

    @Override
    public void punishPlayer(Identified player, Punishment punishment) {
        List<Punishment> current = getPunishments(player);
        if(current.contains(punishment)) current.remove(punishment);
        List<String> newPunishStringList = new ArrayList<>();
        for(Punishment c : current) {
            try {
                newPunishStringList.add(SerializableSerializer.toString(c));
            } catch(Exception e) {
                plugin.getLogger().warn("Could not write a punishment for player: (" +
                        player.identity().uuid().toString() + ") " + player.identity().examinableName());
            }
        }
        try {
            newPunishStringList.add(SerializableSerializer.toString(punishment));
        } catch(Exception e) {
            plugin.getLogger().warn("Could not write a punishment for player: (" +
                    player.identity().uuid().toString() + ") " + player.identity().examinableName());
        }
        Document punishmentDocument = db.getDocument("punishments", player.identity().uuid().toString());
        if(punishmentDocument == null) {
            punishmentDocument = db.buildDocument(player.identity().uuid().toString(), new Object[][] {
                    {
                            "punishments", newPunishStringList
                    },
                    {
                            "firstPunished", System.currentTimeMillis()
                    }
            });
        }
    }

    @Override
    public void unPunishPlayer(Identified player, Punishment punishment) {
        List<Punishment> current = getPunishments(player);
        if(current.contains(punishment)) current.remove(punishment);
        List<String> newPunishStringList = new ArrayList<>();
        for(Punishment c : current) {
            try {
                newPunishStringList.add(SerializableSerializer.toString(c));
            } catch(Exception e) {
                plugin.getLogger().warn("Could not rewrite a punishment for player: (" +
                        player.identity().uuid().toString() + ") " + player.identity().examinableName());
            }
        }
        Document punishmentDocument = db.getDocument("punishments", player.identity().uuid().toString());
        if(punishmentDocument == null) {
           punishmentDocument = db.buildDocument(player.identity().uuid().toString(), new Object[][] {
                   {
                       "punishments", newPunishStringList
                   },
                   {
                       "firstPunished", System.currentTimeMillis()
                   }
           });
        }
    }

    @Override
    public void unPunishPlayer(Identified player) {
        if(getPunishments(player).size() >= 0) {
            db.deleteOne("punishments", player.identity().uuid().toString());
            punishments.remove(player.identity().uuid());
        }
    }

    @Override
    public List<Punishment> getPunishments(Identified player) {
        if(punishments.containsKey(player.identity().uuid())) return punishments.get(player.identity().uuid());
        Document punishmentDocument = db.getDocument("punishments", player.identity().uuid().toString());
        if(punishmentDocument == null) return new ArrayList<>();
        List<Punishment> toReturn = new ArrayList<>();
        List<String> punishList = punishmentDocument.getList("punishList", String.class);
        for(String punishment : punishList) {
            try {
                PunishmentImpl toAdd = (PunishmentImpl) SerializableSerializer.fromString(punishment);
                toReturn.add(toAdd);
            } catch(Exception e) {
                plugin.getLogger().warn("Could not load a punishment for player: (" +
                player.identity().uuid().toString() + ") " + player.identity().examinableName());
            }
        }
        punishments.put(player.identity().uuid(), toReturn);
        return toReturn;
    }
}

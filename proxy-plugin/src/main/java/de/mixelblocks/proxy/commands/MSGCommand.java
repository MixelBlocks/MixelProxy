package de.mixelblocks.proxy.commands;

import com.velocitypowered.api.command.RawCommand;
import com.velocitypowered.api.proxy.Player;
import de.mixelblocks.proxy.MixelProxyPlugin;
import de.mixelblocks.proxy.MixelSerializer;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class MSGCommand implements RawCommand {
    public static HashMap<UUID, UUID> reply = new HashMap<UUID, UUID>();

    private MixelProxyPlugin plugin;

    public MSGCommand(MixelProxyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation invocation) {
        if(!(invocation.source() instanceof Player)) {
            invocation.source().sendMessage(MixelSerializer.sectionRGB.deserialize(MixelProxyPlugin.prefix + "§cDer Command funktioniert nur als Spieler."));
            return;
        }
        if (!invocation.source().hasPermission("proxy.command.msg")) return;

        String args[] = invocation.arguments().split("\\s+");

        if(args.length > 1) {
            Player sender = (Player) invocation.source();
            Player target = MixelProxyPlugin.getInstance().getServer().getPlayer(args[0]).get();
            if(target == null || !target.isOnlineMode()) {
                invocation.source().sendMessage(
                        MixelSerializer.sectionRGB.deserialize("§8[§5MSG§8] §cDieser Spieler existiert nicht oder ist offline.")
                );
            } else {
                String message = "";
                for (int i = 1; i < args.length; ++i) {
                    message = message + " " + args[i];
                }
                sender.sendMessage(
                        MixelSerializer.sectionRGB.deserialize(
                                "§8[§5MSG§8] §6Du -> " + target.getUsername() + " §8\u00bb§a" + message
                        )
                );
                target.sendMessage(
                        MixelSerializer.sectionRGB.deserialize(
                                "§8[§5MSG§8] §6" + sender.getUsername() + " -> Dir §8\u00bb§a" + message
                        )
                );
                reply.put(sender.getUniqueId(), target.getUniqueId());
                reply.put(target.getUniqueId(), sender.getUniqueId());
            }
        }
        else {
            invocation.source().sendMessage(
                    MixelSerializer.sectionRGB.deserialize("§8[§5MSG§8] §cBenutze /msg <Spieler> <Nachricht>")
            );
        }

    }

    @Override
    public CompletableFuture<List<String>> suggestAsync(Invocation invocation) {
        String args[] = invocation.arguments().split("\\s+");
        List<String> completions = new ArrayList<>();
        if(args.length == 1) {
            List<String> available = new ArrayList<>();
            for(Player online : plugin.getServer().getAllPlayers()) available.add(online.getUsername());
            copyPartialMatches(args[0], available, completions);
            Collections.sort(completions);
            return CompletableFuture.completedFuture(completions);
        }
        return CompletableFuture.completedFuture(completions);
    }

    private void copyPartialMatches(String input, List<String> available, List<String> toAppend) {
        for (String string : available) if (string.toLowerCase().startsWith(input.toLowerCase())) toAppend.add(string);
    }

}

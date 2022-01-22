package de.mixelblocks.proxy.commands;

import com.velocitypowered.api.command.RawCommand;
import com.velocitypowered.api.proxy.Player;
import de.mixelblocks.proxy.MixelProxyPlugin;
import de.mixelblocks.proxy.MixelSerializer;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class ReplyCommand implements RawCommand {

    private MixelProxyPlugin plugin;

    public ReplyCommand(MixelProxyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation invocation) {
        if(!(invocation.source() instanceof Player)) {
            invocation.source().sendMessage(MixelSerializer.sectionRGB.deserialize(MixelProxyPlugin.prefix + "§cDer Command funktioniert nur als Spieler."));
            return;
        }
        if (!invocation.source().hasPermission("proxy.command.reply")) return;
        String args[] = invocation.arguments().split("\\s+");

        if(args.length > 0) {
            Player sender = (Player) invocation.source();
            if(!MSGCommand.reply.containsKey(sender.getUniqueId())) {
                invocation.source().sendMessage(
                        MixelSerializer.sectionRGB.deserialize("§8[§5MSG§8] §cDu hast noch keinem Nutzer geschrieben.")
                );
                return;
            }
            Player target = MixelProxyPlugin.getInstance().getServer().getPlayer(MSGCommand.reply.get(sender.getUniqueId())).get();
            if(target == null || !target.isOnlineMode()) {
                invocation.source().sendMessage(
                        MixelSerializer.sectionRGB.deserialize("§8[§5MSG§8] §cDer letzte Nutzer ist nun offline.")
                );
                return;
            }

            String message = "";
            for (int i = 0; i < args.length; ++i) {
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

        }
        else {
            invocation.source().sendMessage(
                    MixelSerializer.sectionRGB.deserialize("§8[§5MSG§8] §cBenutze /r <Nachricht>")
            );
        }
    }

}


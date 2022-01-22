package de.mixelblocks.proxy.listener;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.proxy.server.ServerPing;
import com.velocitypowered.api.util.Favicon;
import de.mixelblocks.proxy.MixelProxy;
import de.mixelblocks.proxy.MixelProxyPlugin;
import de.mixelblocks.proxy.MixelSerializer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class ProxyPingListener {
    final String versionStringified = "v1.16.5";

    int pingCount = 0;
    int cases = 3;

    static final String HEAD = "&#00EE00Mixel&#00CC00Blocks &#035245Network &r[&l&9UNDER DEVELOPMENT&r]";

    private final MixelProxyPlugin plugin;

    public ProxyPingListener(MixelProxyPlugin plugin) { this.plugin = plugin; }

    @Subscribe(order = PostOrder.LAST)
    public void onProxyPing(final ProxyPingEvent event) {
        final ServerPing ping = event.getPing();
        final ServerPing.Builder builder = ping.asBuilder();
        builder.onlinePlayers(ping.getPlayers().get().getOnline());

        if(plugin.getMaintenanceConfig().get().isActive()) {
            builder.maximumPlayers(0)
                    .version(new ServerPing.Version(ping.getVersion().getProtocol(),
                            "MixelBlocksMC-v1.16.5"))
                    .description(MixelSerializer.ampersandRGB.deserialize(
                            "&cAktuell befinden wir uns im Wartungsmodus." +
                                    "\n&aBitte schaue später erneut vorbei."));
            builder.favicon(new Favicon(toBase64URL(new File("server-icon_red.png"))));
        } else {
            builder.maximumPlayers(200)
                    .version(new ServerPing.Version(ping.getVersion().getProtocol(),
                            "MixelBlocksMC-v1.16.5"));

            switch(pingCount) {
                case 0: {
                    builder.description(MixelSerializer.ampersandRGB.deserialize(
                            HEAD +
                                    "\n&rJeder ist willkommen! Trete uns JETZT bei."
                    ));
                    builder.favicon(new Favicon(toBase64URL(new File("server-icon_green.png"))));
                    pingCount++;
                    break;
                }
                case 1: {
                    builder.description(MixelSerializer.ampersandRGB.deserialize(
                            HEAD +
                                    "\n&rBesuche unsere Website https://mixelblocks.de/"
                    ));
                    builder.favicon(new Favicon(toBase64URL(new File("server-icon_blue.png"))));
                    pingCount++;
                    break;
                }
                case 2: {
                    builder.description(MixelSerializer.ampersandRGB.deserialize(
                            HEAD +
                                    "\n&rSurvival und Citybuild neu erleben!"
                    ));
                    builder.favicon(new Favicon(toBase64URL(new File("server-icon_yellow.png"))));
                    pingCount++;
                    break;
                }
                default: {
                    builder.description(MixelSerializer.ampersandRGB.deserialize(
                            HEAD +
                                    "\n&rUnser Web Dashboard https://dash.mixelblocks.de/"
                    ));
                    builder.favicon(new Favicon(toBase64URL(new File("server-icon_purple.png"))));
                    pingCount++;
                    if(pingCount > cases) pingCount = 0;
                    break;
                }
            }
        }



        event.setPing(builder.build());
    }

    static String toBase64URL(File file) {
        BufferedImage image;
        try {
            image = ImageIO.read(file);
        } catch(Exception ex) { return null; }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] imageBytes;
        try {
            ImageIO.write(image, "png", bos);
            imageBytes = bos.toByteArray();
            bos.close();
        } catch(Exception eee) { return null; }

        return "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);
    }

}

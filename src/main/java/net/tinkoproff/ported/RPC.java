package net.tinkoproff.ported;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class RPC {
    private static final DiscordRichPresence discordRichPresence = new DiscordRichPresence();

    public static void startRPC() {
        DiscordEventHandlers eventHandlers = new DiscordEventHandlers.Builder()
            .setDisconnectedCallback((errorCode, message) -> 
                System.out.println("Discord RPC disconnected, error: " + errorCode + ", message: " + message))
            .build();

        String discordID = "838078740344471623";
        DiscordRPC.discordInitialize(discordID, eventHandlers, true);

        discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;
        discordRichPresence.details = "WP3 - Ported by Tinkoprof (respect to travisF)";
        discordRichPresence.largeImageKey = "logo";
        discordRichPresence.largeImageText = "Ported by Tinkoprof";
        discordRichPresence.smallImageKey = "small";
        discordRichPresence.smallImageText = "discord.gg/klux";
        DiscordRPC.discordUpdatePresence(discordRichPresence);
    }

    public static void stopRPC() {
        DiscordRPC.discordShutdown();
    }
}

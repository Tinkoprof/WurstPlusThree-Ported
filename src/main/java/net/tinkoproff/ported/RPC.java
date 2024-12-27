package net.tinkoproff.ported;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;

public class RPC {
    private static final DiscordRichPresence discordRichPresence = new DiscordRichPresence();
    private static final DiscordRPC lib = DiscordRPC.INSTANCE;

    public static void startRPC() {
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> System.out.println("Discord RPC is ready");
        handlers.disconnected = (errorCode, message) -> System.out.println("Discord RPC disconnected, error: " + errorCode + ", message: " + message);
        lib.Discord_Initialize("838078740344471623", handlers, true, null);

        discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;
        discordRichPresence.details = "WP3 - Ported by Tinkoprof (respect to travisF)";
        discordRichPresence.largeImageKey = "logo";
        discordRichPresence.largeImageText = "Ported by Tinkoprof";
        discordRichPresence.smallImageKey = "small";
        discordRichPresence.smallImageText = "discord.gg/klux";
        lib.Discord_UpdatePresence(discordRichPresence);
    }

    public static void stopRPC() {
        lib.Discord_Shutdown();
    }
}

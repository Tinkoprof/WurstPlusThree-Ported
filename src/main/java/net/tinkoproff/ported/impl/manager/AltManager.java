package net.tinkoproff.ported.impl.manager;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;
import net.minecraft.client.util.Session.AccountType;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Optional;

/**
 * @ported by Tinkoprof
 * @author Madmegsox1
 * @since 01/05/2021
 */

public class AltManager {

    private final MinecraftClient mc;

    public AltManager() {
        this.mc = MinecraftClient.getInstance();
    }

    public void loginCracked(String username) {
        try {
            Session session = new Session(
                username,                        // username
                username,                        // uuid
                "",                             // accessToken
                Optional.of("mojang"),          // xuid
                Optional.empty(),               // clientId
                AccountType.LEGACY              // accountType
            );
            setSession(session);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loginMojang(String email, String password) {
        try {
            URL url = new URL("https://authserver.mojang.com/authenticate");
            // ... auth işlemleri ...

            JsonObject resultObject = JsonParser.parseReader(
                new BufferedReader(new InputStreamReader(url.openStream()))
            ).getAsJsonObject();

            String token = resultObject.get("accessToken").getAsString();
            String mcname = resultObject.get("mcname").getAsString();
            String uuid = resultObject.get("uuid").getAsString();
            
            Session session = new Session(
                mcname,                          // username
                uuid,                           // uuid
                token,                          // accessToken
                Optional.of("mojang"),          // xuid
                Optional.empty(),               // clientId
                AccountType.MOJANG              // accountType
            );
            setSession(session);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSession(Session newSession) {
        try {
            // Session field'ını reflection ile değiştir
            java.lang.reflect.Field sessionField = MinecraftClient.class.getDeclaredField("session");
            sessionField.setAccessible(true);
            sessionField.set(mc, newSession);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package net.tinkoproff.ported.impl.manager;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.tinkoproff.ported.WurstPlus;
import net.tinkoproff.ported.impl.util.elements.Alt;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * @ported by Tinkoprof
 * @author Madmegsox1
 * @since 01/05/2021
 */

public class AltManager {

    ArrayList<Alt> alts;
    String altApi = "https://auth.mcleaks.net/v1/";
    private final MinecraftClient mc;

    public AltManager() {
        this.mc = MinecraftClient.getInstance();
        alts = new ArrayList<>();
    }

    public void addAlt(String u, String p) {
        alts.add(new Alt(u, p));
    }

    public void removeAlt(Alt alt) {
        alts.remove(alt);
    }

    public boolean login(Alt alt) {
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) new YggdrasilAuthenticationService(
                Proxy.NO_PROXY, ""
        ).createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(alt.getUsername());
        auth.setPassword(alt.getPassword());
        try {
            auth.logIn();
            Session session = new Session(
                    auth.getSelectedProfile().getName(),
                    auth.getSelectedProfile().getId().toString(),
                    auth.getAuthenticatedToken(),
                    "mojang",
                    null,
                    null
            );
            setSession(session);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean loginCracked(String alt) {
        Session crackedSession = new Session(
            alt,
            alt,
            "0",
            "legacy",
            null,
            null
        );
        try {
            setSession(crackedSession);
            WurstPlus.LOGGER.info("Logged in as " + alt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean loginMcLeaks(String token) {
        if (token.length() != 16) return false;
        JsonObject requestObject = new JsonObject();
        requestObject.add("token", new GsonBuilder().create().toJsonTree(token));
        String response;
        try {
            response = postJson(altApi + "redeem", requestObject.toString());
            JsonObject responseObject;
            responseObject = new JsonParser().parse(response).getAsJsonObject();
            if (!responseObject.get("success").getAsBoolean()) {
                return false;
            }
            JsonObject resultObject = responseObject.get("result").getAsJsonObject();
            Session session = new Session(resultObject.get("mcname").getAsString(), resultObject.get("session").getAsString(), token, "mojang");
            setSession(session);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    private void setSession(Session newSession){
        try {
            Class<? extends MinecraftClient> mc = MinecraftClient.getInstance().getClass();
            Field sessionField = null;

            for (Field field : mc.getDeclaredFields()) {
                if (field.getType().isInstance(newSession)) {
                    sessionField = field;
                    WurstPlus.LOGGER.info("Session field bulundu.");
                }
            }

            if (sessionField == null) {
                throw new IllegalStateException("Session field'ı bulunamadı: " + Session.class.getCanonicalName());
            }

            sessionField.setAccessible(true);
            sessionField.set(MinecraftClient.getInstance(), newSession);
            sessionField.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String postJson(String urlString, String content) {
        try {
            URL url = new URL(urlString);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8);
            writer.write(content);
            writer.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
            connection.disconnect();
            return builder.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Alt> getAlts() {
        return this.alts;
    }
}

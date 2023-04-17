package io.bans.plugin.platform;

import com.google.gson.Gson;
import io.bans.platform.enums.PlatformLogLevel;
import io.bans.platform.validation.PlatformPlayer;
import io.bans.platform.validation.PlatformValidator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

public class BukkitPlatformValidator implements PlatformValidator {

    private final BukkitPlatform bukkitPlatform;
    private final Gson GSON = new Gson();

    /**
     * Constructs a new BukkitPlatformValidator with the provided BukkitPlatform instance.
     * @param bukkitPlatform the BukkitPlatform instance to be used by this manager
     */
    public BukkitPlatformValidator(BukkitPlatform bukkitPlatform) {
        this.bukkitPlatform = bukkitPlatform;
    }

    @Override
    public PlatformPlayer getPlayer(UUID uuid) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(String.format("%s/server/player/%s", bukkitPlatform.getAPI_URL(), uuid.toString())).openConnection();

            connection.setRequestMethod("GET");

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                in.close();
                connection.disconnect();

                String jsonString = content.toString();

                bukkitPlatform.log(PlatformLogLevel.INFO, String.format("JSON String: %s", jsonString));

                return new PlatformPlayer("", uuid.toString(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            }
        } catch (Exception e) {
            bukkitPlatform.log(PlatformLogLevel.ERROR, String.format("An error occurred while attempting to retrieve player data for %s", uuid.toString()));
        }

        return null;
    }

    @Override
    public PlatformPlayer getPlayer(String name) {
        return null;
    }

    @Override
    public boolean isPlayerOnline(UUID uuid) {
        return false;
    }

    @Override
    public boolean isPlayerOnline(String name) {
        return false;
    }

    @Override
    public boolean isPlayerBanned(UUID uuid) {
        return false;
    }

    @Override
    public boolean isPlayerBanned(String name) {
        return false;
    }

    @Override
    public boolean isPlayerMuted(UUID uuid) {
        return false;
    }

    @Override
    public boolean isPlayerMuted(String name) {
        return false;
    }

    @Override
    public PlatformPlayer getHistory(UUID uuid) {
        return null;
    }

    @Override
    public PlatformPlayer getHistory(String name) {
        return null;
    }
}
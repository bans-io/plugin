package io.bans.plugin.platform;

import io.bans.platform.domain.PlatformManager;
import io.bans.platform.enums.PlatformLogLevel;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * BukkitPlatformManager is a Bukkit-specific implementation of the PlatformManager
 * interface, handling user sessions and moderation actions for a Minecraft Bukkit server.
 */
public class BukkitPlatformManager implements PlatformManager {
    private final BukkitPlatform bukkitPlatform;

    /**
     * Constructs a new BukkitPlatformManager with the provided BukkitPlatform instance.
     * @param bukkitPlatform the BukkitPlatform instance to be used by this manager
     */
    public BukkitPlatformManager(BukkitPlatform bukkitPlatform) {
        this.bukkitPlatform = bukkitPlatform;
    }

    private void makeHttpPostRequestWithJsonPayload(String url, JsonObject payload, String serverToken) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("X-SERVER-TOKEN", serverToken);
            connection.setDoOutput(true);

            // Send JSON payload
            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = payload.toString().getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }

            // Check response code
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed to make HTTP POST request, response code: " + responseCode);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to make HTTP POST request", e);
        }
    }

    /**
     * Starts a new user session with the provided UUID, username, and domain.
     * @param uuid the unique identifier for the user
     * @param username the name of the user
     * @param domain the domain associated with the user
     */
    @Override
    public void startSession(UUID uuid, String username, String domain) {
        String serverToken = bukkitPlatform.getConfiguration().getServerKey();

        // Build JSON payload
        JsonObject payload = new JsonObject();
        payload.addProperty("joined_at", Instant.now().toString());
        payload.addProperty("minecraft_uuid", uuid.toString());
        payload.addProperty("minecraft_username", username);
        payload.addProperty("domain_connected_from", domain);

        // Set up HTTP connection and send request
        try {
            String url = String.format("%s/minecraft/server/sessions/create", bukkitPlatform.getAPI_URL());
            makeHttpPostRequestWithJsonPayload(url, payload, serverToken);
        } catch (Exception e) {
            bukkitPlatform.log(PlatformLogLevel.ERROR, String.format("Failed to start session for %s: %s", username, e));
        }
    }

    /**
     * Ends the current server players session.
     * @param reason the reason for ending the session
     */
    @Override
    public void endSession(String reason) {
        String serverToken = bukkitPlatform.getConfiguration().getServerKey();

        // Build JSON payload
        JsonObject payload = new JsonObject();
        payload.addProperty("disconnected_at", Instant.now().toString());
        payload.addProperty("reason", reason);

        // Set up HTTP connection and send request
        try {
            String url = String.format("%s/minecraft/server/sessions/terminate-all", bukkitPlatform.getAPI_URL());
            makeHttpPostRequestWithJsonPayload(url, payload, serverToken);
        } catch (Exception e) {
            bukkitPlatform.log(PlatformLogLevel.ERROR, String.format("Failed to end sessions for server: %s", e));
        }
    }

    /**
     * Ends the session for the provided UUID and username.
     * @param uuid the unique identifier for the user
     * @param username the name of the user
     */
    @Override
    public void endSession(UUID uuid, String username) {
        String serverToken = bukkitPlatform.getConfiguration().getServerKey();

        // Build JSON payload
        JsonObject payload = new JsonObject();
        payload.addProperty("disconnected_at", Instant.now().toString());
        payload.addProperty("reason", "Disconnected from the server");

        // Set up HTTP connection and send request
        try {
            String url = String.format("%s/minecraft/server/sessions/terminate", bukkitPlatform.getAPI_URL());
            makeHttpPostRequestWithJsonPayload(url, payload, serverToken);
        } catch (Exception e) {
            bukkitPlatform.log(PlatformLogLevel.ERROR, String.format("Failed to end session for %s: %s", username, e));
        }
    }

    /**
     * Bans a user from the platform for a specified duration.
     * @param uuid the unique identifier for the user
     * @param username the name of the user
     * @param reason the reason for the ban
     * @param duration duration the duration of the ban in milliseconds
     * @return a CompletableFuture containing the result of the ban action
     */
    @Override
    public CompletableFuture<Boolean> ban(UUID uuid, String username, String reason, long duration) {

        // 

        return null;
    }

    /**
     * Removes a user's ban from the platform.
     * @param uuid the unique identifier for the user
     * @param username the name of the user
     * @param reason the reason for the unban
     * @return a CompletableFuture containing the result of the unban action
     */
    @Override
    public CompletableFuture<Boolean> unban(UUID uuid, String username, String reason) {
        return null;
    }

    /**
     * Temporarily or indefinitely prevents a user from chatting on the platform for a specified duration.
     * @param uuid the unique identifier for the user
     * @param username the name of the user
     * @param reason the reason for the timeout
     * @param duration the duration of the timeout in milliseconds
     * @return a CompletableFuture containing the result of the chat mute action
     */
    @Override
    public CompletableFuture<Boolean> mute(UUID uuid, String username, String reason, long duration) {
        return null;
    }

    /**
     * Removes a user's chat mute from the platform.
     * @param uuid the unique identifier for the user
     * @param username the name of the user
     * @param reason the reason for the unmute
     * @return a CompletableFuture containing the result of the chat unmute action
     */
    @Override
    public CompletableFuture<Boolean> unmute(UUID uuid, String username, String reason) {
        return null;
    }

    /**
     * Kicks a user from the platform with a specified reason.
     * @param uuid the unique identifier for the user
     * @param username the name of the user
     * @param reason the reason for the kick
     * @return a CompletableFuture containing the result of the kick action
     */
    @Override
    public CompletableFuture<Boolean> kick(UUID uuid, String username, String reason) {
        return null;
    }

    /**
     * Kicks all users from the platform with a specified reason.
     * @param reason the reason for the kick
     * @return a CompletableFuture containing the result of the kick action
     */
    @Override
    public CompletableFuture<Boolean> kickall(String reason) {
        return null;
    }
}
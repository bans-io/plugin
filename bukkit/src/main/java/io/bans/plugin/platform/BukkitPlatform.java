package io.bans.plugin.platform;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.bans.platform.domain.Platform;
import io.bans.platform.enums.PlatformLogLevel;
import io.bans.platform.enums.PlatformType;
import io.bans.platform.utils.VersionUtil;

import io.bans.plugin.BukkitPlugin;
import io.bans.plugin.command.base.BansCommand;
import io.bans.plugin.command.core.*;
import io.bans.platform.utils.TimeFormat;
import io.bans.plugin.configuration.BukkitPlatformConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static io.bans.platform.utils.ResourceUtil.getBundledFile;

/**
 * The platform implementation for Bukkit.
 */
public class BukkitPlatform implements Platform {

    private final int API_VERSION = 1;
    private final String API_URL = String.format("http://localhost:3000/api/v%d", API_VERSION);
    private final TimeFormat TIMEFORMAT = new TimeFormat();
    private final BukkitPlugin bukkitPlugin;
    private final BukkitPlatformConfiguration platformConfiguration;

    private boolean debugMode;

    /**
     * Creates a new platform implementation.
     * @param bukkitPlugin The plugin object.
     */
    public BukkitPlatform(BukkitPlugin bukkitPlugin) {
        this.bukkitPlugin = bukkitPlugin;
        this.platformConfiguration = new BukkitPlatformConfiguration(this, getBundledFile(this, bukkitPlugin.getDataFolder(), "config.yml"));
        this.debugMode = false;
    }

    /**
     * Gets the type of platform.
     *
     * @return The platform type.
     */
    @Override
    public PlatformType getType() {
        return PlatformType.BUKKIT;
    }

    /**
     * Sets up the platform.
     *
     * @param key The key to use for the platform.
     * @return Whether the platform was set up successfully.
     */
    @Override
    public boolean setup(String key) {
        if (key == null || key.isEmpty()) {
            return false;
        }

        try {
            URL url = new URL(String.format("%s/minecraft/server", API_URL));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-SERVER-TOKEN", key);

            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                return true;
            } else if (responseCode == 401) {
                return false;
            } else {
                this.log(PlatformLogLevel.ERROR, String.format("Failed to retrieve server information, unexpected response code: %d", responseCode));
                return false;
            }
        } catch (IOException e) {
            this.log(PlatformLogLevel.ERROR, String.format("Failed to retrieve server information: %s", e.getMessage()));
            return false;
        }
    }

    /**
     * Checks whether the platform is running the latest version of its type.
     *
     * @param currentVersion The current version of the platform.
     * @return Whether the platform is running the latest version of its type.
     */
    @Override
    public String isRunningLatestVersion(String currentVersion) {

        if (currentVersion == null || currentVersion.isEmpty()) {
            return null;
        }

        try {
            URL url = new URL(String.format("%s/plugin/minecraft/%s", API_URL, getType().name().toLowerCase()));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                // Check if the current version is the latest
                if (VersionUtil.isNewerVersion(currentVersion, response.toString())) {
                    return response.toString();
                } else {
                    return null;
                }
            } else {
                this.log(PlatformLogLevel.ERROR, String.format("Failed to retrieve plugin information, unexpected response code: %d", responseCode));
                return null;
            }
        } catch (IOException e) {
            this.log(PlatformLogLevel.ERROR, String.format("Failed to retrieve plugin information: %s", e.getMessage()));
            return null;
        }
    }

    /**
     * Starts the platform.
     */
    @Override @SuppressWarnings("ConstantConditions")
    public void start() {

        // Load configuration
        this.platformConfiguration.load();

        // Check if server is set up
        if (!setup(platformConfiguration.getServerKey())) {
            log(PlatformLogLevel.ERROR, "Ensure the server key is valid and properly configured.");
            return;
        }

        String latestVersion = isRunningLatestVersion(bukkitPlugin.getDescription().getVersion());

        // Check if the server is running the latest version of the plugin
        if (latestVersion != null) {
            log(PlatformLogLevel.WARN, String.format("This server is running v%s, an outdated version of Bans.", bukkitPlugin.getDescription().getVersion()));
            log(PlatformLogLevel.WARN, String.format("Download the latest at: https://downloads.bans.io/bukkit/%s", latestVersion));
        }

        // Configure platform based on online dashboard configuration
        this.platformConfiguration.configure(getServerConfiguration());

        // Register commands
        bukkitPlugin.getCommand("bans").setExecutor(new BansCommand(this));

        bukkitPlugin.getCommand("ban").setExecutor(new BanCommand(this));
        bukkitPlugin.getCommand("check").setExecutor(new CheckCommand(this));
        bukkitPlugin.getCommand("history").setExecutor(new HistoryCommand(this));
        bukkitPlugin.getCommand("kick").setExecutor(new KickCommand(this));
        bukkitPlugin.getCommand("kickall").setExecutor(new KickAllCommand(this));
        bukkitPlugin.getCommand("timeout").setExecutor(new TimeoutCommand(this));
        bukkitPlugin.getCommand("unban").setExecutor(new UnbanCommand(this));
        bukkitPlugin.getCommand("warn").setExecutor(new WarnCommand(this));

    }

    private JsonObject getServerConfiguration() {
        try {
            URL url = new URL(String.format("%s/minecraft/server/configuration", API_URL));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-SERVER-TOKEN", platformConfiguration.getServerKey());

            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                return JsonParser.parseString(response.toString()).getAsJsonObject();
            } else {
                this.log(PlatformLogLevel.ERROR, String.format("Failed to retrieve server information, unexpected response code: %d", responseCode));
                return null;
            }
        } catch (IOException e) {
            this.log(PlatformLogLevel.ERROR, String.format("Failed to retrieve server information: %s", e.getMessage()));
            return null;
        }
    }

    /**
     * Stops the platform.
     */
    @Override
    public void stop() {

    }

    /**
     * Logs a message to the console.
     * @param level The log level.
     * @param message The log message.
     */
    @Override
    public void log(PlatformLogLevel level, String message) {
        switch (level) {
            case INFO:
                bukkitPlugin.getLogger().info(message);
                break;
            case WARN:
                bukkitPlugin.getLogger().warning(message);
                break;
            case ERROR:
                bukkitPlugin.getLogger().severe(message);
        }
    }

    /**
     * Gets the configuration for the platform.
     * @return The platform configuration.
     */
    @Override
    public BukkitPlatformConfiguration getConfiguration() {
        return platformConfiguration;
    }

    /**
     * Gets the time formatter for the platform.
     *
     * @return The time formatter.
     */
    public TimeFormat getTimeFormatter() {
        return TIMEFORMAT;
    }

    /**
     * Gets whether the platform is in debug mode.
     *
     * @return Whether the platform is in debug mode.
     */
    public boolean isDebugMode() {
        return debugMode;
    }

    /**
     * Sets whether the platform is in debug mode.
     *
     * @param debugMode Whether the platform is in debug mode.
     */
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    /**
     * Gets the version of the plugin.
     *
     * @return the version
     */
    public String getVersion() {
        return bukkitPlugin.getDescription().getVersion();
    }
}

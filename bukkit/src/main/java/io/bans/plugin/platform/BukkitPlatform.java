package io.bans.plugin.platform;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.bans.platform.domain.Platform;
import io.bans.platform.enums.PlatformLogLevel;
import io.bans.platform.enums.PlatformType;
import io.bans.platform.utils.TimeFormat;
import io.bans.platform.utils.VersionUtil;
import io.bans.plugin.BukkitPlugin;
import io.bans.plugin.command.base.BansCommand;
import io.bans.plugin.command.core.*;
import io.bans.plugin.configuration.BukkitPlatformConfiguration;
import io.bans.plugin.event.PlayerJoinListener;
import io.bans.plugin.event.PlayerQuitListener;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static io.bans.platform.utils.ResourceUtil.getBundledFile;

/**
 * The Bukkit-specific platform implementation.
 */
public class BukkitPlatform implements Platform {

    private final int API_VERSION = 1;
    private final String API_URL = String.format("http://localhost:3000/api/v%d", API_VERSION);
    private final TimeFormat TIMEFORMAT = new TimeFormat();
    private final BukkitPlugin bukkitPlugin;
    private final BukkitPlatformConfiguration platformConfiguration;
    private final BukkitPlatformManager platformManager;
    private final BukkitPlatformValidator platformValidator;
    private boolean setup;
    private boolean debugMode;

    /**
     * Instantiates a new platform implementation.
     * @param bukkitPlugin The plugin instance associated with this platform.
     */
    public BukkitPlatform(BukkitPlugin bukkitPlugin) {
        this.bukkitPlugin = bukkitPlugin;
        this.platformConfiguration = new BukkitPlatformConfiguration(this, getBundledFile(this, bukkitPlugin.getDataFolder(), "config.yml"));
        this.platformManager = new BukkitPlatformManager(this);
        this.platformValidator = new BukkitPlatformValidator(this);
        this.setup = false;
        this.debugMode = false;
    }

    public String getAPI_URL() {
        return API_URL;
    }

    /**
     * Retrieves the platform type.
     * @return The type of the platform.
     */
    @Override
    public PlatformType getType() {
        return PlatformType.BUKKIT;
    }

    /**
     * Configures the platform using the provided key.
     * @param key The configuration key for the platform.
     * @return Whether the platform was configured successfully.
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

            switch (responseCode) {
                case 200:
                    return true;
                case 401:
                    // Handle 401 response code
                    break;
                default:
                    this.log(PlatformLogLevel.ERROR, String.format("Failed to retrieve server information, unexpected response code: %d", responseCode));
                    break;
            }

            return false;
        } catch (IOException e) {
            this.log(PlatformLogLevel.ERROR, String.format("Failed to retrieve server information: %s", e.getMessage()));
            return false;
        }
    }

    /**
     * Determines if the platform is running the most recent version of its type.
     * @param currentVersion The current version of the platform.
     * @return Whether the platform is running the latest version available for its type.
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
     * Initiates the platform.
     */
    @Override @SuppressWarnings("ConstantConditions")
    public void start() {

        // Register commands
        bukkitPlugin.getCommand("bans").setExecutor(new BansCommand(this));

        bukkitPlugin.getCommand("ban").setExecutor(new BanCommand(this));
        bukkitPlugin.getCommand("check").setExecutor(new CheckCommand(this));
        bukkitPlugin.getCommand("history").setExecutor(new HistoryCommand(this));
        bukkitPlugin.getCommand("kick").setExecutor(new KickCommand(this));
        bukkitPlugin.getCommand("kickall").setExecutor(new KickAllCommand(this));
        bukkitPlugin.getCommand("mute").setExecutor(new MuteCommand(this));
        bukkitPlugin.getCommand("unban").setExecutor(new UnbanCommand(this));
        bukkitPlugin.getCommand("warn").setExecutor(new WarnCommand(this));

        // Load configuration
        this.platformConfiguration.load();

        if (!(setup = setup(platformConfiguration.getServerKey()))) {
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

        // Send a session end request to the backend for any players online from a previous session
        this.getManager().endSession("Server restart detected");

        // Register events
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), bukkitPlugin);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(this), bukkitPlugin);
    }

    /**
     * Retrieves the server configuration as a JsonObject.
     * @return The server configuration in a JsonObject format.
     */
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
     * Shuts down the platform.
     */
    @Override
    public void stop() {
        // Send a session end request to the backend for any players currently online
        this.getManager().endSession("Server shutdown detected");
    }

    /**
     * Outputs a log message to the console.
     * @param level The desired log level.
     * @param message The message to be logged.
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
     * Retrieves the configuration settings for the platform.
     * @return The platform configuration.
     */
    @Override
    public BukkitPlatformConfiguration getConfiguration() {
        return platformConfiguration;
    }

    /**
     * Obtains the manager responsible for the platform.
     * @return The platform manager instance.
     */
    @Override
    public BukkitPlatformManager getManager() {
        return platformManager;
    }

    /**
     * Obtains the validator responsible for the platform.
     * @return The platform validator instance.
     */
    @Override
    public BukkitPlatformValidator getValidator() {
        return platformValidator;
    }

    /**
     * Acquires the time formatter used by the platform.
     * @return The time formatter instance.
     */
    public TimeFormat getTimeFormatter() {
        return TIMEFORMAT;
    }

    /**
     * Determines if the platform is properly set up.
     * @return True if the platform is set up, otherwise false.
     */
    public boolean isSetup() {
        return setup;
    }

    /**
     * Checks if the platform is operating in debug mode.
     * @return True if the platform is in debug mode, otherwise false.
     */
    public boolean isDebugMode() {
        return debugMode;
    }

    /**
     * Assigns the debug mode status for the platform.
     * @param debugMode The debug mode status to be assigned.
     */
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    /**
     * Retrieves the version number of the plugin.
     * @return The plugin version.
     */
    public String getVersion() {
        return bukkitPlugin.getDescription().getVersion();
    }
}

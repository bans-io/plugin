package io.bans.plugin.platform;

import io.bans.platform.Platform;
import io.bans.platform.PlatformConfiguration;
import io.bans.platform.PlatformLogLevel;
import io.bans.platform.PlatformType;
import io.bans.platform.command.PlatformCommand;
import io.bans.plugin.BansPlugin;
import io.bans.plugin.platform.command.base.BansCommand;

import java.util.HashMap;

import static io.bans.platform.util.ResourceUtil.getBundledFile;

/**
 * The platform implementation for Bukkit.
 */
public class PlatformImpl implements Platform {

    private final BansPlugin bansPlugin;
    private final PlatformConfigurationImpl platformConfiguration;

    private final HashMap<String, PlatformCommand> commands = new HashMap<>();

    private boolean debugMode;

    /**
     * Creates a new platform implementation.
     * @param bansPlugin The plugin object.
     */
    public PlatformImpl(BansPlugin bansPlugin) {
        this.bansPlugin = bansPlugin;
        this.platformConfiguration = new PlatformConfigurationImpl(this, getBundledFile(this, bansPlugin.getDataFolder(), "config.yml"));
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
     * Starts the platform.
     */
    @Override
    public void start() {

        // Load configuration
        this.platformConfiguration.load();

        // Register commands
        bansPlugin.getCommand("bans").setExecutor(new BansCommand(this));

        // Outdated version of the plugin
//        log(PlatformLogLevel.WARN, String.format("This server is running v%s, an outdated version of Bans.", bansPlugin.getDescription().getVersion()));
//        log(PlatformLogLevel.WARN, String.format("Download the latest at: https://downloads.bans.io/bukkit/%s", "todo"));
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
                bansPlugin.getLogger().info(message);
                break;
            case WARN:
                bansPlugin.getLogger().warning(message);
                break;
            case ERROR:
                bansPlugin.getLogger().severe(message);
        }
    }

    @Override
    public PlatformConfiguration getConfiguration() {
        return platformConfiguration;
    }

    /**
     * Gets the platform's commands.
     *
     * @return The platform commands map.
     */
    public HashMap<String, PlatformCommand> getCommands() {
        return commands;
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
        return bansPlugin.getDescription().getVersion();
    }
}

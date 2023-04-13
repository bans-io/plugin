package io.bans.platform.domain;

import io.bans.platform.enums.PlatformLogLevel;
import io.bans.platform.enums.PlatformType;
import io.bans.platform.configuration.PlatformConfiguration;

/**
 * Interface for platform-specific functionality that can be used by a plugin.
 */
public interface Platform {

    /**
     * Returns the type of the platform.
     *
     * @return The platform type.
     */
    PlatformType getType();

    /**
     * Sets up the platform.
     *
     * @param key The key to use for the platform.
     * @return Whether the platform was set up successfully.
     */
    boolean setup(String key);

    /**
     * Checks whether the platform is running the latest version of its type.
     *
     * @param currentVersion The current version of the platform.
     * @return The latest version of the platform, or null if the platform is running the latest version.
     */
    String isRunningLatestVersion(String currentVersion);

    /**
     * Starts the platform.
     */
    void start();

    /**
     * Stops the platform.
     */
    void stop();

    /**
     * Logs a message with the specified level.
     *
     * @param level The log level.
     * @param message The log message.
     */
    void log(PlatformLogLevel level, String message);

    /**
     * Returns the platform's configuration.
     *
     * @return The platform's configuration.
     */
    PlatformConfiguration getConfiguration();
}
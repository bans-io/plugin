package io.bans.platform.domain;

import io.bans.platform.enums.PlatformLogLevel;
import io.bans.platform.enums.PlatformType;
import io.bans.platform.configuration.PlatformConfiguration;

import io.bans.platform.validation.PlatformValidator;

/**
 * Interface for platform-specific functionality that can be used by a plugin.
 */
public interface Platform {

    /**
     * Retrieves the classification of the platform.
     * @return A PlatformType enumeration representing the specific category of the platform.
     */
    PlatformType getType();

    /**
     * Sets up the platform.
     * @param key The key to use for the platform.
     * @return Whether the platform was set up successfully.
     */
    boolean setup(String key);

    /**
     * Checks whether the platform is running the latest version of its type.
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
     * @param level The log level.
     * @param message The log message.
     */
    void log(PlatformLogLevel level, String message);

    /**
     * Retrieves the current configuration settings for the platform.
     * @return A PlatformConfiguration object containing the platform's configuration settings.
     */
    PlatformConfiguration getConfiguration();

    /**
     * Provides access to the platform's management functionality.
     * @return A PlatformManager object responsible for managing the platform's resources and processes.
     */
    PlatformManager getManager();

    /**
     * Obtains the platform's validation utility.
     * @return A PlatformValidator object responsible for validating platform-related data and operations.
     */
    PlatformValidator getValidator();
}
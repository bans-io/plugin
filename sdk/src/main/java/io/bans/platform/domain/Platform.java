package io.bans.platform.domain;

import io.bans.platform.enums.PlatformLogLevel;
import io.bans.platform.enums.PlatformType;
import io.bans.platform.configuration.PlatformConfiguration;

import io.bans.platform.validation.PlatformValidator;

/**
 * Interface for platform-specific functionality.
 */
public interface Platform {

    /**
     * Retrieves the platform type.
     * @return The type of the platform.
     */
    PlatformType getType();

    /**
     * Configures the platform using the provided key.
     * @param key The configuration key for the platform.
     * @return Whether the platform was configured successfully.
     */
    boolean setup(String key);

    /**
     * Determines if the platform is running the most recent version of its type.
     * @param currentVersion The current version of the platform.
     * @return Whether the platform is running the latest version available for its type.
     */
    String isRunningLatestVersion(String currentVersion);

    /**
     * Initiates the platform.
     */
    void start();

    /**
     * Shuts down the platform.
     */
    void stop();

    /**
     * Outputs a log message to the console.
     * @param level The desired log level.
     * @param message The message to be logged.
     */
    void log(PlatformLogLevel level, String message);

    /**
     * Retrieves the configuration settings for the platform.
     * @return The platform configuration.
     */
    PlatformConfiguration getConfiguration();

    /**
     * Obtains the manager responsible for the platform.
     * @return The platform manager instance.
     */
    PlatformManager getManager();

    /**
     * Obtains the validator responsible for the platform.
     * @return The platform validator instance.
     */
    PlatformValidator getValidator();
}
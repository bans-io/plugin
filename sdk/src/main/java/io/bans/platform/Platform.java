package io.bans.platform;

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
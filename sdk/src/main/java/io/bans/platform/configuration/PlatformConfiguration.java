package io.bans.platform.configuration;

import dev.dejvokep.boostedyaml.YamlDocument;

/**
 * Interface for platform-specific configuration that can be used by a plugin.
 */
public interface PlatformConfiguration {
    /**
     * Get the configuration document
     * @return the configuration document.
     */
    YamlDocument get();

    /**
     * Load the configuration
     */
    void load();

    /**
     * Reload the configuration
     */
    void reload();
}
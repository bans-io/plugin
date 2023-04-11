package io.bans.plugin.platform;

import dev.dejvokep.boostedyaml.YamlDocument;
import io.bans.platform.Platform;
import io.bans.platform.PlatformConfiguration;
import io.bans.platform.PlatformLogLevel;

import java.io.File;

public class PlatformConfigurationImpl implements PlatformConfiguration {

    private final Platform platform;
    private final YamlDocument config;
    private String serverKey;

    /**
     * Create a new platform configuration
     * @param platform The platform
     * @param configFile The configuration file
     */
    public PlatformConfigurationImpl(Platform platform, File configFile) {
        this.platform = platform;

        try {
            this.config = YamlDocument.create(configFile);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration file", e);
        }
    }

    /**
     * Get the configuration document
     *
     * @return The configuration document.
     */
    @Override
    public YamlDocument get() {
        return config;
    }

    /**
     * Load the configuration
     */
    @Override
    public void load() {
        try {
            // Check config version
            int configVersion = config.getInt("config_version");

            if (configVersion != 1) {
                platform.log(PlatformLogLevel.ERROR, "Your config is outdated. Please delete it and restart the server.");
                return;
            }

            serverKey = config.getString("server_key");
        } catch (Exception e) {
            platform.log(PlatformLogLevel.ERROR, String.format("Failed to load configuration file: %s", e));
        }
    }

    /**
     * Reload the configuration
     */
    @Override
    public void reload() {
        try {
            config.reload();
        } catch (Exception e) {
            platform.log(PlatformLogLevel.ERROR, String.format("Failed to reload configuration file: %s", e));
        }
    }

    /**
     * Get the configured server key
     *
     * @return The server key
     */
    public String getServerKey() {
        return serverKey;
    }
}
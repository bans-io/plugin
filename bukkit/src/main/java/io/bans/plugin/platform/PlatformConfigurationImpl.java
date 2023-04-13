package io.bans.plugin.platform;

import com.google.gson.JsonObject;
import dev.dejvokep.boostedyaml.YamlDocument;
import io.bans.platform.Platform;
import io.bans.platform.PlatformConfiguration;
import io.bans.platform.PlatformLogLevel;
import io.bans.platform.PlatformTemplate;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PlatformConfigurationImpl implements PlatformConfiguration {

    private final Platform platform;
    private final YamlDocument config;
    private String serverKey;

    private final Map<String, Map<String, PlatformTemplate>> templates = new HashMap<>();

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
     * Configure the platform with data from the API
     * @param serverData The server data
     */
    public void configure(JsonObject serverData) {
        templates.clear();
    }

    /**
     * Get the configured server key
     *
     * @return The server key
     */
    public String getServerKey() {
        return serverKey;
    }

    /**
     * Get the configured templates
     * @return The templates
     */
    public Map<String, Map<String, PlatformTemplate>> getTemplates() {
        return templates;
    }

    /**
     * Get the configured templates for a specific type
     * @param type The type
     * @return The templates
     */
    public Map<String, PlatformTemplate> getTemplates(String type) {
        return templates.get(type);
    }

    /**
     * Get a specific template
     * @param type The type
     * @param name The name
     * @return The template
     */
    public PlatformTemplate getTemplate(String type, String name) {
        return templates.get(type).get(name);
    }
}
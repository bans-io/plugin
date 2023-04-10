package io.bans.plugin;

import io.bans.platform.PlatformLogLevel;
import io.bans.plugin.platform.PlatformImpl;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The Bukkit plugin implementation of Bans.
 * <p>
 * Written by kgm (kylegrahammatzen) on April 8, 2023.
 * @since 1.0.0
 */
public class BansPlugin extends JavaPlugin {
    private final PlatformImpl platform;

    /**
     * Instantiates a new BansPlugin object.
     */
    public BansPlugin() {
        this.platform = new PlatformImpl(this);
    }

    /**
     * Called when the plugin is enabled.
     * Starts the platform object.
     */
    @Override
    public void onEnable() {
        try {
            this.platform.start();
        } catch (Exception e) {
            this.platform.log(PlatformLogLevel.ERROR, String.format("Error starting platform %s %s", platform.getType(), e));
        }
    }

    /**
     * Called when the plugin is disabled.
     * Stops the platform object.
     */
    @Override
    public void onDisable() {
        try {
            this.platform.stop();
        } catch (Exception e) {
            this.platform.log(PlatformLogLevel.ERROR, String.format("Error stopping platform %s %s", platform.getType(), e));
        }
    }
}
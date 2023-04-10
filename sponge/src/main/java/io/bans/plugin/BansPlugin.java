package io.bans.plugin;

import io.bans.plugin.platform.PlatformImpl;

/**
 * The Bukkit plugin implementation of Bans.
 * <p>
 * Written by kgm (kylegrahammatzen) on April 10, 2023.
 * @since 1.0.0
 */
public class BansPlugin {
    private final PlatformImpl platform;

    /**
     * Instantiates a new BansPlugin object.
     */
    public BansPlugin() {
        this.platform = new PlatformImpl(this);
    }
}
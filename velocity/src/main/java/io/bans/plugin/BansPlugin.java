package io.bans.plugin;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import io.bans.platform.PlatformLogLevel;
import io.bans.plugin.platform.PlatformImpl;

import java.nio.file.Path;
import java.util.logging.Logger;

@Plugin(
        id = "bans",
        name = "Bans",
        version = "1.0.0",
        description = "The official Minecraft plugin for Bans.",
        url = "https://bans.io",
        authors = {"kgm"}
)
public class BansPlugin {
    private final PlatformImpl platform;

    @Inject
    public BansPlugin(ProxyServer proxy, Logger logger, @DataDirectory Path dataDirectory) {
        this.platform = new PlatformImpl(this, proxy, logger, dataDirectory);
    }

    @Subscribe
    public void onEnable(ProxyInitializeEvent event) {
        try {
            this.platform.start();
        } catch (Exception e) {
            this.platform.log(PlatformLogLevel.ERROR, String.format("Error starting platform %s %s", platform.getType(), e));
        }
    }

    @Subscribe
    public void onDisable(ProxyShutdownEvent event) {
        try {
            this.platform.stop();
        } catch (Exception e) {
            this.platform.log(PlatformLogLevel.ERROR, String.format("Error stopping platform %s %s", platform.getType(), e));
        }
    }
}
package io.bans.plugin.platform;

import com.velocitypowered.api.proxy.ProxyServer;
import io.bans.platform.domain.Platform;
import io.bans.platform.configuration.PlatformConfiguration;
import io.bans.platform.domain.PlatformManager;
import io.bans.platform.enums.PlatformLogLevel;
import io.bans.platform.enums.PlatformType;
import io.bans.platform.validation.PlatformValidator;
import io.bans.plugin.BansPlugin;

import java.nio.file.Path;
import java.util.logging.Logger;
public class PlatformImpl implements Platform {
    private final BansPlugin bansPlugin;
    private final ProxyServer proxy;
    private final Logger logger;
    private final Path dataDirectory;

    public PlatformImpl(BansPlugin bansPlugin, ProxyServer proxy, Logger logger, Path dataDirectory) {
        this.bansPlugin = bansPlugin;
        this.proxy = proxy;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
    }

    @Override
    public PlatformType getType() {
        return PlatformType.VELOCITY;
    }

    @Override
    public boolean setup(String key) {
        return false;
    }

    @Override
    public String isRunningLatestVersion(String currentVersion) {
        return null;
    }

    @Override
    public void start() {

        // Outdated version of the plugin
//        log(PlatformLogLevel.WARN, String.format("This server is running v%s, an outdated version of Bans.", bansPlugin.getDescription().getVersion()));
//        log(PlatformLogLevel.WARN, String.format("Download the latest at: https://downloads.bans.io/bukkit/%s", "todo"));
    }

    @Override
    public void stop() {

    }

    @Override
    public void log(PlatformLogLevel level, String message) {
        switch (level) {
            case INFO:
                logger.info(message);
                break;
            case WARN:
                logger.warning(message);
                break;
            case ERROR:
                logger.severe(message);
        }
    }

    @Override
    public PlatformConfiguration getConfiguration() {
        return null;
    }

    @Override
    public PlatformManager getManager() {
        return null;
    }

    @Override
    public PlatformValidator getValidator() {
        return null;
    }
}
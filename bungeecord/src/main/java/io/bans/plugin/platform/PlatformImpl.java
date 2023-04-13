package io.bans.plugin.platform;

import io.bans.platform.domain.Platform;
import io.bans.platform.configuration.PlatformConfiguration;
import io.bans.platform.enums.PlatformLogLevel;
import io.bans.platform.enums.PlatformType;
import io.bans.plugin.BansPlugin;

public class PlatformImpl implements Platform {

    private final BansPlugin bansPlugin;

    public PlatformImpl(BansPlugin bansPlugin) {
        this.bansPlugin = bansPlugin;
    }

    @Override
    public PlatformType getType() {
        return PlatformType.BUNGEECORD;
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
                bansPlugin.getLogger().info(message);
                break;
            case WARN:
                bansPlugin.getLogger().warning(message);
                break;
            case ERROR:
                bansPlugin.getLogger().severe(message);
        }
    }

    @Override
    public PlatformConfiguration getConfiguration() {
        return null;
    }
}

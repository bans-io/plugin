package io.bans.plugin.platform;

import io.bans.platform.Platform;
import io.bans.platform.PlatformLogLevel;
import io.bans.platform.PlatformType;
import io.bans.plugin.BansPlugin;
public class PlatformImpl implements Platform {

    private final BansPlugin bansPlugin;

    public PlatformImpl(BansPlugin bansPlugin) {
        this.bansPlugin = bansPlugin;
    }

    @Override
    public PlatformType getType() {
        return PlatformType.BUKKIT;
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
}

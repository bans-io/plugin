package io.bans.plugin.platform;

import io.bans.platform.validation.PlatformPlayer;
import io.bans.platform.validation.PlatformValidator;

import java.util.UUID;

public class BukkitPlatformValidator implements PlatformValidator {

    private final BukkitPlatform bukkitPlatform;
    public BukkitPlatformValidator(BukkitPlatform bukkitPlatform) {
        this.bukkitPlatform = bukkitPlatform;
    }

    @Override
    public PlatformPlayer getPlayer(UUID uuid) {
        return null;
    }

    @Override
    public PlatformPlayer getPlayer(String name) {
        return null;
    }

    @Override
    public boolean isPlayerOnline(UUID uuid) {
        return false;
    }

    @Override
    public boolean isPlayerBanned(UUID uuid) {
        return false;
    }

    @Override
    public boolean isPlayerMuted(UUID uuid) {
        return false;
    }

    @Override
    public PlatformPlayer getHistory(UUID uuid) {
        return null;
    }

    @Override
    public PlatformPlayer getHistory(String name) {
        return null;
    }
}
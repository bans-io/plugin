package io.bans.plugin.platform;

import io.bans.platform.validation.PlatformPlayer;
import io.bans.platform.validation.PlatformValidator;

import java.util.ArrayList;
import java.util.UUID;

public class BukkitPlatformValidator implements PlatformValidator {

    private final BukkitPlatform bukkitPlatform;

    /**
     * Constructs a new BukkitPlatformValidator with the provided BukkitPlatform instance.
     * @param bukkitPlatform the BukkitPlatform instance to be used by this manager
     */
    public BukkitPlatformValidator(BukkitPlatform bukkitPlatform) {
        this.bukkitPlatform = bukkitPlatform;
    }

    @Override
    public PlatformPlayer getPlayer(UUID uuid) {
        return new PlatformPlayer("", uuid.toString(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
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
    public boolean isPlayerOnline(String name) {
        return false;
    }

    @Override
    public boolean isPlayerBanned(UUID uuid) {
        return false;
    }

    @Override
    public boolean isPlayerBanned(String name) {
        return false;
    }

    @Override
    public boolean isPlayerMuted(UUID uuid) {
        return false;
    }

    @Override
    public boolean isPlayerMuted(String name) {
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
package io.bans.plugin.platform;

import io.bans.platform.domain.PlatformManager;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class BukkitPlatformManager implements PlatformManager {
    private final BukkitPlatform bukkitPlatform;

    public BukkitPlatformManager(BukkitPlatform bukkitPlatform) {
        this.bukkitPlatform = bukkitPlatform;
    }

    @Override
    public void startSession(UUID uuid, String username, String domain) {

    }

    @Override
    public void endSession(UUID uuid, String username) {

    }

    @Override
    public CompletableFuture<Boolean> ban(UUID uuid, String username, String reason, long duration) {

        // 

        return null;
    }

    @Override
    public CompletableFuture<Boolean> unban(UUID uuid, String username, String reason) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> timeout(UUID uuid, String username, String reason, long duration) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> untimeout(UUID uuid, String username, String reason) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> kick(UUID uuid, String username, String reason) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> kickall(String reason) {
        return null;
    }
}
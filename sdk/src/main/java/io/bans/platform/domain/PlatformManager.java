package io.bans.platform.domain;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PlatformManager {

    void startSession(UUID uuid, String username, String domain);
    void endSession(UUID uuid, String username);
    CompletableFuture<Boolean> ban(UUID uuid, String username, String reason, long duration);
    CompletableFuture<Boolean> unban(UUID uuid, String username, String reason);
    CompletableFuture<Boolean> timeout(UUID uuid, String username, String reason, long duration);
    CompletableFuture<Boolean> untimeout(UUID uuid, String username, String reason);
    CompletableFuture<Boolean> kick(UUID uuid, String username, String reason);
    CompletableFuture<Boolean> kickall(String reason);
}
package io.bans.platform.validation;

import java.util.UUID;

/**
 * Validates a player's existence and status on the platform.
 */
public interface PlatformValidator {

    PlatformPlayer getPlayer(UUID uuid);
    PlatformPlayer getPlayer(String name);

    boolean isPlayerOnline(UUID uuid);

    boolean isPlayerBanned(UUID uuid);

    boolean isPlayerMuted(UUID uuid);

    PlatformPlayer getHistory(UUID uuid);

    PlatformPlayer getHistory(String name);



}
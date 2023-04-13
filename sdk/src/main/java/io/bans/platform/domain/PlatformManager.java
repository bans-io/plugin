package io.bans.platform.domain;

import java.util.UUID;

public interface PlatformManager {
    void ban(UUID uuid, String username, String reason, long duration);
    void unban(UUID uuid, String username, String reason);

    void timeout(UUID uuid, String username, String reason, long duration);
    void untimeout(UUID uuid, String username, String reason);

    void kick(UUID uuid, String username, String reason);
    void kickall(String reason);
}
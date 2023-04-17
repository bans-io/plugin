package io.bans.platform.punishments;

import java.time.LocalDateTime;
public class PlatformBan {
    private final long id;
    private final long playerId;
    private final long bannedBy;
    private final long templateId;
    private final String reason;
    private final LocalDateTime banTime;
    private final LocalDateTime expirationTime;

    public PlatformBan(long id, long playerId, long bannedBy, long templateId, String reason, LocalDateTime banTime, LocalDateTime expirationTime) {
        this.id = id;
        this.playerId = playerId;
        this.bannedBy = bannedBy;
        this.templateId = templateId;
        this.reason = reason;
        this.banTime = banTime;
        this.expirationTime = expirationTime;
    }

    public long getId() {
        return id;
    }

    public long getPlayerId() {
        return playerId;
    }

    public long getBannedBy() {
        return bannedBy;
    }

    public long getTemplateId() {
        return templateId;
    }

    public String getReason() {
        return reason;
    }

    public LocalDateTime getBanTime() {
        return banTime;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }
}
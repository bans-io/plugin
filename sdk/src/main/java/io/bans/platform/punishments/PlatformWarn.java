package io.bans.platform.punishments;

import java.time.LocalDateTime;

public class PlatformWarn {
    private final long id;
    private final long playerId;
    private final long warnedBy;
    private final long templateId;
    private final String reason;
    private final LocalDateTime warningTime;
    private final LocalDateTime expirationTime;

    public PlatformWarn(long id, long playerId, long warnedBy, long templateId, String reason, LocalDateTime warningTime, LocalDateTime expirationTime) {
        this.id = id;
        this.playerId = playerId;
        this.warnedBy = warnedBy;
        this.templateId = templateId;
        this.reason = reason;
        this.warningTime = warningTime;
        this.expirationTime = expirationTime;
    }

    public long getId() {
        return id;
    }

    public long getPlayerId() {
        return playerId;
    }

    public long getWarnedBy() {
        return warnedBy;
    }

    public long getTemplateId() {
        return templateId;
    }

    public String getReason() {
        return reason;
    }

    public LocalDateTime getWarningTime() {
        return warningTime;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }
}
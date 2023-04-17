package io.bans.platform.punishments;

import java.time.LocalDateTime;

public class PlatformMute {
    private final long id;
    private final long playerId;
    private final long mutedBy;
    private final long templateId;
    private final String reason;
    private final int duration;
    private final LocalDateTime mutedTime;
    private final LocalDateTime expirationTime;

    public PlatformMute(long id, long playerId, long mutedBy, long templateId, String reason, int duration, LocalDateTime mutedTime, LocalDateTime expirationTime) {
        this.id = id;
        this.playerId = playerId;
        this.mutedBy = mutedBy;
        this.templateId = templateId;
        this.reason = reason;
        this.duration = duration;
        this.mutedTime = mutedTime;
        this.expirationTime = expirationTime;
    }

    public long getId() {
        return id;
    }

    public long getPlayerId() {
        return playerId;
    }

    public long getMutedBy() {
        return mutedBy;
    }

    public long getTemplateId() {
        return templateId;
    }

    public String getReason() {
        return reason;
    }

    public int getDuration() {
        return duration;
    }

    public LocalDateTime getMutedTime() {
        return mutedTime;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }
}
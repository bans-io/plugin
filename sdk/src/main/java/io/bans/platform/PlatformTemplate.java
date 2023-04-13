package io.bans.platform;

public class PlatformTemplate {
    private String name;
    private PlatformTemplateType templateType;
    private String reason;
    private long duration;
    public PlatformTemplate(String name, String reason, long duration) {
        this.name = name;
        this.reason = reason;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
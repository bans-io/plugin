package io.bans.platform.validation;

import io.bans.platform.punishments.PlatformBan;
import io.bans.platform.punishments.PlatformMute;
import io.bans.platform.punishments.PlatformWarn;

import java.util.List;

public class PlatformPlayer {
    private final String name;
    private final String uuid;
    private final List<PlatformBan> bansList;
    private final List<PlatformWarn> warnsList;
    private final List<PlatformMute> mutesList;

    public PlatformPlayer(String name, String uuid, List<PlatformBan> bansList, List<PlatformWarn> warnsList, List<PlatformMute> mutesList) {
        this.name = name;
        this.uuid = uuid;
        this.bansList = bansList;
        this.warnsList = warnsList;
        this.mutesList = mutesList;
    }
    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    public List<PlatformBan> getBans() {
        return bansList;
    }

    public List<PlatformWarn> getWarns() {
        return warnsList;
    }

    public List<PlatformMute> getTimeouts() {
        return mutesList;
    }

    public boolean isBanned() {
        return !bansList.isEmpty() && bansList.get(0).isExpired();
    }

    public String getBanReason() {
        return bansList.get(0).getReason();
    }
}
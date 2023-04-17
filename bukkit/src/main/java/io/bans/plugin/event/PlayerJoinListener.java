package io.bans.plugin.event;

import io.bans.platform.enums.PlatformLogLevel;
import io.bans.platform.validation.PlatformPlayer;
import io.bans.plugin.platform.BukkitPlatform;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerJoinListener implements Listener {

    private final BukkitPlatform bukkitPlatform;

    public PlayerJoinListener(BukkitPlatform bukkitPlatform) {
        this.bukkitPlatform = bukkitPlatform;
    }

    @EventHandler
    public void onPlayerLogin(final @NotNull PlayerLoginEvent event) {
        Player player = event.getPlayer();

        // Check if player is banned
        PlatformPlayer platformPlayer = bukkitPlatform.getValidator().getPlayer(player.getUniqueId());

        if (platformPlayer.getBans().size() > 0) {
            player.kickPlayer(platformPlayer.getBans().get(0).getReason());
            return;
        }

        String hostName = event.getHostname();

        if (hostName.indexOf('\0') != -1) {
            hostName = hostName.substring(0, hostName.indexOf('\0'));
        }

        // Send session start request to Bans
        bukkitPlatform.getManager().startSession(player.getUniqueId(), player.getName(), hostName);

        // Debug mode
        if (bukkitPlatform.isDebugMode()) {
            bukkitPlatform.log(PlatformLogLevel.INFO, String.format("[DEBUG] Session initiated for player: %s", player.getName()));
            bukkitPlatform.log(PlatformLogLevel.INFO, String.format("[DEBUG] Player connected from host: %s", hostName));
        }
    }
}
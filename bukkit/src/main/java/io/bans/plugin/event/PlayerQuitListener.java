package io.bans.plugin.event;

import io.bans.platform.enums.PlatformLogLevel;
import io.bans.plugin.platform.BukkitPlatform;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final BukkitPlatform bukkitPlatform;

    public PlayerQuitListener(BukkitPlatform bukkitPlatform) {
        this.bukkitPlatform = bukkitPlatform;
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        // Send session end request to Bans
        bukkitPlatform.getManager().endSession(player.getUniqueId(), player.getName());

        // Debug mode
        if (bukkitPlatform.isDebugMode()) {
            bukkitPlatform.log(PlatformLogLevel.INFO, String.format("Session terminated for player: %s", player.getName()));
        }
    }
}
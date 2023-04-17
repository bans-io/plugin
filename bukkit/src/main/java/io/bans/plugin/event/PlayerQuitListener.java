package io.bans.plugin.event;

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
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        bukkitPlatform.getManager().endSession(player.getUniqueId(), player.getName());
    }
}
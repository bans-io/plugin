package io.bans.plugin.event;

import io.bans.platform.validation.PlatformPlayer;
import io.bans.plugin.platform.BukkitPlatform;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final BukkitPlatform bukkitPlatform;

    public PlayerJoinListener(BukkitPlatform bukkitPlatform) {
        this.bukkitPlatform = bukkitPlatform;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Check if player is banned
        PlatformPlayer platformPlayer = bukkitPlatform.getValidator().getPlayer(player.getUniqueId());

        if (platformPlayer.getBans().size() > 0) {
            player.kickPlayer(platformPlayer.getBans().get(0).getReason());
        }

        String domain = "";

        if (player.getAddress() != null) {
            domain = player.getAddress().getHostName();
        }

        // Send request to API with player data
        bukkitPlatform.getManager().startSession(player.getUniqueId(), player.getName(), domain);

    }
}
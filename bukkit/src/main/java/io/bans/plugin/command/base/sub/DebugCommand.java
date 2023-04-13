package io.bans.plugin.command.base.sub;

import io.bans.platform.command.PlatformCommand;
import io.bans.plugin.platform.BukkitPlatform;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class DebugCommand extends PlatformCommand {

    /**
     * Constructor for the DebugCommand.
     * @param platform The platform instance.
     */
    public DebugCommand(BukkitPlatform platform) {
        super("debug", "Toggles Bans plugin-wide debug mode.", commandContext -> {
            CommandSender sender = (CommandSender) commandContext.getSender();

            platform.setDebugMode(!platform.isDebugMode());

            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c[Bans] &7Debug mode is now " + (platform.isDebugMode() ? "&aenabled" : "&cdisabled") + "&7."));
        });
    }
}
package io.bans.plugin.platform.command.base.sub;

import io.bans.platform.command.PlatformCommand;
import io.bans.plugin.platform.PlatformImpl;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class DebugCommand extends PlatformCommand {
    public DebugCommand(PlatformImpl platform) {
        super("debug", "Toggles Bans plugin-wide debug mode.", commandContext -> {
            CommandSender sender = (CommandSender) commandContext.getSender();

            platform.setDebugMode(!platform.isDebugMode());

            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c[Bans] &7Debug mode is now " + (platform.isDebugMode() ? "&aenabled" : "&cdisabled") + "&7."));
        });
    }
}
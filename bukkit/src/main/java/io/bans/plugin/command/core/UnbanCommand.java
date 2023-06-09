package io.bans.plugin.command.core;

import io.bans.plugin.platform.BukkitPlatform;
import io.bans.plugin.utils.BasePlatformCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class UnbanCommand extends BasePlatformCommand {

    /**
     * Constructor for the UnbanCommand.
     * @param platform The platform instance.
     */
    public UnbanCommand(BukkitPlatform platform) {
        super("unban", "Unbans a player.", commandContext -> {
            CommandSender sender = (CommandSender) commandContext.getSender();

            if (!platform.isSetup()) {
                sender.sendMessage(ChatColor.RED + "Bans is not setup yet. Please try again later.");
                return;
            }



            sender.sendMessage("This command is not yet implemented.");
        });
    }
}
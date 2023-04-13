package io.bans.plugin.command.core;

import io.bans.plugin.platform.BukkitPlatform;
import io.bans.plugin.utils.BasePlatformCommand;
import org.bukkit.command.CommandSender;

public class HistoryCommand extends BasePlatformCommand {

    /**
     * Constructor for the HistoryCommand.
     * @param platform The platform instance.
     */
    public HistoryCommand(BukkitPlatform platform) {
        super("history", "Shows a player's history.", commandContext -> {
            CommandSender sender = (CommandSender) commandContext.getSender();

            sender.sendMessage("This command is not yet implemented.");
        });
    }
}
package io.bans.plugin.platform.command.core;

import io.bans.plugin.platform.PlatformImpl;
import io.bans.plugin.platform.util.BasePlatformCommand;
import org.bukkit.command.CommandSender;

public class HistoryCommand extends BasePlatformCommand {

    public HistoryCommand(PlatformImpl platform) {
        super("history", "Shows a player's history.", commandContext -> {
            CommandSender sender = (CommandSender) commandContext.getSender();

            sender.sendMessage("This command is not yet implemented.");
        });
    }
}
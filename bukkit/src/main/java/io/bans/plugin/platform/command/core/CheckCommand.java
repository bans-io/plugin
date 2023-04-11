package io.bans.plugin.platform.command.core;

import io.bans.plugin.platform.PlatformImpl;
import io.bans.plugin.platform.util.BasePlatformCommand;
import org.bukkit.command.CommandSender;

public class CheckCommand extends BasePlatformCommand {

    /**
     * Constructor for the CheckCommand.
     * @param platform The platform instance.
     */
    public CheckCommand(PlatformImpl platform) {
        super("check", "Checks a player's status.", commandContext -> {
            CommandSender sender = (CommandSender) commandContext.getSender();

            sender.sendMessage("This command is not yet implemented.");
        });
    }
}
package io.bans.plugin.command.core;

import io.bans.plugin.platform.BukkitPlatform;
import io.bans.plugin.utils.BasePlatformCommand;
import org.bukkit.command.CommandSender;

public class CheckCommand extends BasePlatformCommand {

    /**
     * Constructor for the CheckCommand.
     * @param platform The platform instance.
     */
    public CheckCommand(BukkitPlatform platform) {
        super("check", "Checks a player's status.", commandContext -> {
            CommandSender sender = (CommandSender) commandContext.getSender();

            sender.sendMessage("This command is not yet implemented.");
        });
    }
}
package io.bans.plugin.command.core;

import io.bans.plugin.platform.BukkitPlatform;
import io.bans.plugin.utils.BasePlatformCommand;
import org.bukkit.command.CommandSender;

public class TimeoutCommand extends BasePlatformCommand {

    /**
     * Constructor for the TimeoutCommand.
     * @param platform The platform instance.
     */
    public TimeoutCommand(BukkitPlatform platform) {
        super("timeout", "Timeout/mute a player.", commandContext -> {
            CommandSender sender = (CommandSender) commandContext.getSender();

            sender.sendMessage("This command is not yet implemented.");
        });
    }
}
package io.bans.plugin.platform.command.core;

import io.bans.plugin.platform.PlatformImpl;
import io.bans.plugin.platform.util.BasePlatformCommand;
import org.bukkit.command.CommandSender;

public class TimeoutCommand extends BasePlatformCommand {

    /**
     * Constructor for the TimeoutCommand.
     * @param platform The platform instance.
     */
    public TimeoutCommand(PlatformImpl platform) {
        super("timeout", "Timeout/mute a player.", commandContext -> {
            CommandSender sender = (CommandSender) commandContext.getSender();

            sender.sendMessage("This command is not yet implemented.");
        });
    }
}
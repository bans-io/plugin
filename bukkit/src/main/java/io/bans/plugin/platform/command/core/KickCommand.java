package io.bans.plugin.platform.command.core;

import io.bans.plugin.platform.PlatformImpl;
import io.bans.plugin.platform.util.BasePlatformCommand;
import org.bukkit.command.CommandSender;

public class KickCommand extends BasePlatformCommand {

    /**
     * Constructor for the KickCommand.
     *
     * @param platform The platform instance.
     */
    public KickCommand(PlatformImpl platform) {
        super("kick", "Kick a player.", commandContext -> {
            CommandSender sender = (CommandSender) commandContext.getSender();

            sender.sendMessage("This command is not yet implemented.");
        });
    }
}
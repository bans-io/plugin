package io.bans.plugin.platform.command.core;

import io.bans.plugin.platform.PlatformImpl;
import io.bans.plugin.platform.util.BasePlatformCommand;
import org.bukkit.command.CommandSender;

public class UnbanCommand extends BasePlatformCommand {

    /**
     * Constructor for the UnbanCommand.
     * @param platform The platform instance.
     */
    public UnbanCommand(PlatformImpl platform) {
        super("unban", "Unbans a player.", commandContext -> {
            CommandSender sender = (CommandSender) commandContext.getSender();

            sender.sendMessage("This command is not yet implemented.");
        });
    }
}
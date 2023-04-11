package io.bans.plugin.platform.command.core;

import io.bans.plugin.platform.PlatformImpl;
import io.bans.plugin.platform.util.BasePlatformCommand;
import org.bukkit.command.CommandSender;

public class BanCommand extends BasePlatformCommand {

    /**
     * Constructor for the BanCommand.
     * @param platform The platform instance.
     */
    public BanCommand(PlatformImpl platform) {
        super("ban", "Bans a player.", commandContext -> {
            CommandSender sender = (CommandSender) commandContext.getSender();

            sender.sendMessage("This command is not yet implemented.");
        });
    }
}
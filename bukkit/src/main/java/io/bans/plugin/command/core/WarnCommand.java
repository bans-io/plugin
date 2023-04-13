package io.bans.plugin.command.core;

import io.bans.plugin.platform.BukkitPlatform;
import io.bans.plugin.utils.BasePlatformCommand;
import org.bukkit.command.CommandSender;

public class WarnCommand extends BasePlatformCommand {

    /**
     * Constructor for the WarnCommand.
     * @param platform The platform instance.
     */
    public WarnCommand(BukkitPlatform platform) {
        super("warn", "Warns a player.", commandContext -> {
            CommandSender sender = (CommandSender) commandContext.getSender();

            sender.sendMessage("This command is not yet implemented.");
        });
    }
}
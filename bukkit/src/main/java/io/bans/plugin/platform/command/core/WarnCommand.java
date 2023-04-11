package io.bans.plugin.platform.command.core;

import io.bans.plugin.platform.PlatformImpl;
import io.bans.plugin.platform.util.BasePlatformCommand;
import org.bukkit.command.CommandSender;

public class WarnCommand extends BasePlatformCommand {

    public WarnCommand(PlatformImpl platform) {
        super("warn", "Warns a player.", commandContext -> {
            CommandSender sender = (CommandSender) commandContext.getSender();

            sender.sendMessage("This command is not yet implemented.");
        });
    }
}
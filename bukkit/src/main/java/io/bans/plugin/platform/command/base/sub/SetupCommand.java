package io.bans.plugin.platform.command.base.sub;

import io.bans.platform.command.PlatformCommand;
import io.bans.plugin.platform.PlatformImpl;
import org.bukkit.command.CommandSender;

public class SetupCommand extends PlatformCommand {

    public SetupCommand(PlatformImpl platform) {
        super("setup", "Configure the server token key", commandContext -> {
            CommandSender sender = (CommandSender) commandContext.getSender();

            sender.sendMessage("This command is not yet implemented.");
        });
    }
}
package io.bans.plugin.command.base.sub;

import io.bans.platform.command.PlatformCommand;
import io.bans.plugin.platform.BukkitPlatform;
import org.bukkit.command.CommandSender;

public class SetupCommand extends PlatformCommand {

    /**
     * Constructor for the SetupCommand.
     * @param platform The platform instance.
     */
    public SetupCommand(BukkitPlatform platform) {
        super("setup", "Configure the server token key", commandContext -> {
            CommandSender sender = (CommandSender) commandContext.getSender();

            sender.sendMessage("This command is not yet implemented.");
        });
    }
}
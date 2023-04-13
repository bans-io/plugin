package io.bans.plugin.platform.command.core;
import io.bans.plugin.platform.PlatformImpl;
import io.bans.plugin.platform.util.BasePlatformCommand;
import org.bukkit.command.CommandSender;

public class KickAllCommand extends BasePlatformCommand {

    /**
     * Constructor for the KickAllCommand.
     *
     * @param platform The platform instance.
     */
    public KickAllCommand(PlatformImpl platform) {
        super("kickall", "Kicks all players.", commandContext -> {
            CommandSender sender = (CommandSender) commandContext.getSender();

            sender.sendMessage("This command is not yet implemented.");
        });
    }
}
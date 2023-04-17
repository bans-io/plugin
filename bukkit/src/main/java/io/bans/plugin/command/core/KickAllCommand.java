package io.bans.plugin.command.core;
import io.bans.plugin.platform.BukkitPlatform;
import io.bans.plugin.utils.BasePlatformCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class KickAllCommand extends BasePlatformCommand {

    /**
     * Constructor for the KickAllCommand.
     *
     * @param platform The platform instance.
     */
    public KickAllCommand(BukkitPlatform platform) {
        super("kickall", "Kicks all players.", commandContext -> {
            CommandSender sender = (CommandSender) commandContext.getSender();

            if (!platform.isSetup()) {
                sender.sendMessage(ChatColor.RED + "Bans is not setup yet. Please try again later.");
                return;
            }

            sender.sendMessage("This command is not yet implemented.");
        });
    }
}
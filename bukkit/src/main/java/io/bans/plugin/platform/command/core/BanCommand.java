package io.bans.plugin.platform.command.core;

import io.bans.platform.PlatformTemplate;
import io.bans.plugin.platform.PlatformImpl;
import io.bans.plugin.platform.util.BasePlatformCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class BanCommand extends BasePlatformCommand {

    /**
     * Constructor for the BanCommand.
     * @param platform The platform instance.
     */
    public BanCommand(PlatformImpl platform) {
        super("ban", "Bans a player.", commandContext -> {
            CommandSender sender = (CommandSender) commandContext.getSender();

            if (commandContext.getArguments().length < 1) {
                sender.sendMessage(ChatColor.RED + "Usage: /ban <player> [-d <duration>] [-r <reason>] [-t <template>]");
                return;
            }

            OfflinePlayer target = Bukkit.getOfflinePlayer(commandContext.getArguments()[0]);
            if (!target.hasPlayedBefore()) {
                sender.sendMessage(ChatColor.RED + "Player not found.");
                return;
            }

            String[] args = commandContext.getArguments();
            String reason = "";
            long duration = -1L;
            PlatformTemplate template = null;

            for (int i = 1; i < args.length; i++) {
                if ("-d".equalsIgnoreCase(args[i]) && i + 1 < args.length) {
                    duration = platform.getTimeFormatter().getDuration(args[++i]);
                } else if ("-r".equalsIgnoreCase(args[i]) && i + 1 < args.length) {
                    reason = args[++i];
                } else if ("-t".equalsIgnoreCase(args[i]) && i + 1 < args.length) {
                    String templateName = args[++i];

                    if (platform.getConfiguration().getTemplate("ban", templateName) == null) {
                        sender.sendMessage(ChatColor.RED + "Template not found.");
                        return;
                    }

                    template = platform.getConfiguration().getTemplate("ban", templateName);
                    duration = template.getDuration();
                    reason = template.getReason();

                    break;
                }
            }

            if (duration >= 0) {
                //platform.getManager().ban(target.getUniqueId(), sender.getName(), reason, duration);
            } else {
                //platform.getManager().ban(target.getUniqueId(), sender.getName(), reason);
            }
        });
    }
}
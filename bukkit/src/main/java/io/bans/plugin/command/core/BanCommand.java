package io.bans.plugin.command.core;

import io.bans.platform.templates.PlatformTemplate;
import io.bans.platform.validation.PlatformPlayer;
import io.bans.plugin.platform.BukkitPlatform;
import io.bans.plugin.utils.BasePlatformCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

public class BanCommand extends BasePlatformCommand {

    /**
     * Constructor for the BanCommand.
     * @param platform The platform instance.
     */
    public BanCommand(BukkitPlatform platform) {
        super("ban", "Bans a player.", commandContext -> {
            CommandSender sender = (CommandSender) commandContext.getSender();

            if (!platform.isSetup()) {
                sender.sendMessage(ChatColor.RED + "Bans is not setup yet. Please try again later.");
                return;
            }

            if (commandContext.getArguments().length < 1) {
                sender.sendMessage(ChatColor.RED + "Usage: /ban <player> [-d <duration>] [-r <reason>] [-t <template>]");
                return;
            }

            PlatformPlayer platformPlayer = platform.getValidator().getPlayer(commandContext.getArguments()[0]);

            if (platformPlayer == null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("&cPlayer %s has yet to play the server.", commandContext.getArguments()[0])));
                return;
            }

            String[] args = commandContext.getArguments();
            String reason = "";
            AtomicLong duration = new AtomicLong(-1);
            PlatformTemplate template;

            for (int i = 1; i < args.length; i++) {
                if ("-d".equalsIgnoreCase(args[i]) && i + 1 < args.length) {
                    duration.set(platform.getTimeFormatter().getDuration(args[++i]));
                } else if ("-r".equalsIgnoreCase(args[i]) && i + 1 < args.length) {
                    reason = args[++i];
                } else if ("-t".equalsIgnoreCase(args[i]) && i + 1 < args.length) {
                    String templateName = args[++i];

                    if (platform.getConfiguration().getTemplate("ban", templateName) == null) {
                        sender.sendMessage(ChatColor.RED + "Template not found.");
                        return;
                    }

                    template = platform.getConfiguration().getTemplate("ban", templateName);
                    duration.set(template.getDuration());
                    reason = template.getReason();

                    break;
                }
            }

            CompletableFuture<Boolean> banResult = platform.getManager().ban(UUID.fromString(platformPlayer.getUuid()), sender.getName(), reason, duration.get());
            banResult.thenAccept(result -> {
                if (result) {
                    sender.sendMessage(String.format("%s%s has been banned for %s.", ChatColor.GREEN, platformPlayer.getName(), platform.getTimeFormatter().getDuration(duration.get())));
                } else {
                    sender.sendMessage(String.format("%s%s is already banned.", ChatColor.RED, platformPlayer.getName()));
                }
            });
        });
    }
}
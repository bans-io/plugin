package io.bans.plugin.platform.command.base;

import io.bans.platform.command.PlatformCommand;
import io.bans.plugin.platform.PlatformImpl;
import io.bans.plugin.platform.command.base.sub.DebugCommand;
import io.bans.plugin.platform.command.base.sub.SetupCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

public class BansCommand implements CommandExecutor {

    private final PlatformImpl platform;
    private final HashMap<String, PlatformCommand> subCommands = new HashMap<>();

    /**
     * Constructor for the BansCommand.
     * @param platform The platform instance.
     */
    public BansCommand(PlatformImpl platform) {
        this.platform = platform;

        // Register sub commands
        subCommands.put("setup", new SetupCommand(platform));
        subCommands.put("debug", new DebugCommand(platform));
    }

    /**
     * Called when a player executes a command
     * @param sender Source of the command
     * @param command Command which was executed
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("bans.admin")) {
            sender.sendMessage("You do not have permission to use this command.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c[Bans] &7Plugin information:"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(" &8- &7Version: &cv%s&7.", platform.getVersion())));
            return true;
        }

        Optional<PlatformCommand> subCommand = subCommands.values().stream().filter(cmd -> cmd.getName().equalsIgnoreCase(args[0])).findFirst();

        if (!subCommand.isPresent()) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("&c[Bans] &7Unknown command: &c%s&7.", args[0])));
            return true;
        }

        if (!sender.hasPermission("bans." + subCommand.get().getName())) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c[Bans] &7You do not have permission to use this command."));
            return true;
        }

        subCommand.get().execute(sender, Arrays.copyOfRange(args, 1, args.length));
        return true;
    }
}

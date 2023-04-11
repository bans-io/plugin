package io.bans.plugin.platform.util;

import io.bans.platform.command.PlatformCommand;
import io.bans.platform.command.PlatformCommandContext;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class BasePlatformCommand extends PlatformCommand implements CommandExecutor {

    /**
     * Creates a new platform command.
     *
     * @param name            The name of the command.
     * @param description     The description of the command.
     * @param commandExecutor The command consumer.
     */
    public BasePlatformCommand(String name, String description, Consumer<PlatformCommandContext> commandExecutor) {
        super(name, description, commandExecutor);
    }

    /**
     * Executes the given command, returning its success.
     * @param sender Source of the command
     * @param command Command which was executed
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     * @return true if a valid command, otherwise false.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        this.execute(sender, args);
        return true;
    }
}
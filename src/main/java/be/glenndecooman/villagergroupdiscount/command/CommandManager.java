package be.glenndecooman.villagergroupdiscount.command;

import be.glenndecooman.villagergroupdiscount.command.subcommand.GroupCommand;
import be.glenndecooman.villagergroupdiscount.persistence.JPAUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandManager implements CommandExecutor {
    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public CommandManager() {
        subCommands.put("group", new GroupCommand());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) return false;
            String[] slicedArgs = Arrays.copyOfRange(args, 1, args.length);
            SubCommand subCommand = subCommands.get(args[0]);
            if (subCommand == null) return false;
            subCommand.execute(player, slicedArgs);
        }

        return true;
    }
}

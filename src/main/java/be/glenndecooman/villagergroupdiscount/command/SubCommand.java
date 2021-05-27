package be.glenndecooman.villagergroupdiscount.command;

import org.bukkit.entity.Player;

public interface SubCommand {
    void execute(Player sender, String[] args);
    default void showUsage(Player player, String usage) {
        player.sendMessage("Usage: " + usage);
    }
}

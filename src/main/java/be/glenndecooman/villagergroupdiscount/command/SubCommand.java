package be.glenndecooman.villagergroupdiscount.command;

import be.glenndecooman.villagergroupdiscount.util.MsgUtil;
import org.bukkit.entity.Player;

public interface SubCommand {
    void execute(Player sender, String[] args);
    void showUsage(Player player);
    default void showUsage(Player player, String usage) {
        String msg = "Usage: " + usage;
        MsgUtil.warning(player, msg);
    }
}

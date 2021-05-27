package be.glenndecooman.villagergroupdiscount.command.subcommand.group;

import be.glenndecooman.villagergroupdiscount.command.SubCommand;
import org.bukkit.entity.Player;

public class CreateGroupCommand implements SubCommand {
    @Override
    public void execute(Player sender, String[] args) {
        if (args.length != 0) {
            sender.sendMessage("Team create");
        } else {
            showUsage(sender, "/vgd group create <name>");
        }
    }
}

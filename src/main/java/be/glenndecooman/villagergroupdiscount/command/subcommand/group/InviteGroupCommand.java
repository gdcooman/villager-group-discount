package be.glenndecooman.villagergroupdiscount.command.subcommand.group;

import be.glenndecooman.villagergroupdiscount.command.SubCommand;
import org.bukkit.entity.Player;

import javax.persistence.EntityManager;

public class InviteGroupCommand implements SubCommand {
    @Override
    public void execute(Player sender, String[] args) {
        if (args.length > 0) {
            sender.sendMessage("Group invite");
        } else {
            showUsage(sender, "/vgd group invite <player>");
        }
    }
}

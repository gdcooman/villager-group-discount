package be.glenndecooman.villagergroupdiscount.command.subcommand.invite;

import be.glenndecooman.villagergroupdiscount.command.SubCommand;
import org.bukkit.entity.Player;

public class DeclineInviteCommand implements SubCommand {
    @Override
    public void execute(Player sender, String[] args) {
        if (args.length < 0) {

        } else {
            showUsage(sender, "/vgd invite accept");
        }
    }
}

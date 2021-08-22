package be.glenndecooman.villagergroupdiscount.command.subcommand;

import be.glenndecooman.villagergroupdiscount.command.SubCommand;
import be.glenndecooman.villagergroupdiscount.command.subcommand.invite.AcceptInviteCommand;
import be.glenndecooman.villagergroupdiscount.command.subcommand.invite.DeclineInviteCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InviteCommand implements SubCommand {
    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public InviteCommand() {
        subCommands.put("accept", new AcceptInviteCommand());
        subCommands.put("decline", new DeclineInviteCommand());
    }
    @Override
    public void execute(Player sender, String[] args) {
        if (args.length > 0)  {
            String[] slicedArgs = Arrays.copyOfRange(args, 1, args.length);
            SubCommand subCommand = subCommands.get(args[0]);
            if (subCommand != null) {
                subCommand.execute(sender, slicedArgs);
            } else {
                showUsage(sender);
            }
        } else {
            showUsage(sender);
        }
    }

    @Override
    public void showUsage(Player player) {
        showUsage(player, "/vgd invite <accept/decline>");
    }
}

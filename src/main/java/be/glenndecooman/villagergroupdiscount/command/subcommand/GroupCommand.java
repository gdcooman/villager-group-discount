package be.glenndecooman.villagergroupdiscount.command.subcommand;

import be.glenndecooman.villagergroupdiscount.command.SubCommand;
import be.glenndecooman.villagergroupdiscount.command.subcommand.group.*;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GroupCommand implements SubCommand {
    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public GroupCommand() {
        subCommands.put("create", new CreateGroupCommand());
        subCommands.put("delete", new DeleteGroupCommand());
        subCommands.put("invite", new InviteGroupCommand());
        subCommands.put("kick", new KickGroupCommand());
        subCommands.put("leave", new LeaveGroupCommand());
        subCommands.put("info", new InfoGroupCommand());
        subCommands.put("transfer", new TransferGroupCommand());
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
        showUsage(player, "/vgd group <subcommand> <argument>");
    }
}

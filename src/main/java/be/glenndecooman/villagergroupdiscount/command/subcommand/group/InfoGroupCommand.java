package be.glenndecooman.villagergroupdiscount.command.subcommand.group;

import be.glenndecooman.villagergroupdiscount.command.SubCommand;
import be.glenndecooman.villagergroupdiscount.model.VGDGroup;
import be.glenndecooman.villagergroupdiscount.model.VGDPlayer;
import be.glenndecooman.villagergroupdiscount.persistence.VGDPlayerDAO;
import be.glenndecooman.villagergroupdiscount.persistence.VGDPlayerDAOImpl;
import be.glenndecooman.villagergroupdiscount.util.JPAUtil;
import be.glenndecooman.villagergroupdiscount.util.MsgUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;

import javax.persistence.EntityManager;
import java.util.Set;
import java.util.stream.Collectors;

public class InfoGroupCommand implements SubCommand {
    @Override
    public void execute(Player sender, String[] args) {
        if (args.length == 0) {
            EntityManager em = JPAUtil.getEntityManager();
            VGDPlayerDAO vgdPlayerDAO = new VGDPlayerDAOImpl(em);

            VGDPlayer player = vgdPlayerDAO.findById(sender.getUniqueId());
            if (player != null) {
                VGDGroup group = player.getGroup();
                if (group != null) {
                    MsgUtil.info(sender, makeInfoMsg(group));
                } else {
                    MsgUtil.error(sender, "You are not in a group.");
                }
            } else {
                MsgUtil.error(sender, "Sorry, something went wrong!");
            }
            em.close();
        } else {
            showUsage(sender);
        }
    }

    private Component makeInfoMsg(VGDGroup group) {
        Component infoMsg = Component.text("\n")
                .append(Component.text(String.format("%s\n\n", group.getName())).decoration(TextDecoration.UNDERLINED, true))
                .append(Component.text(String.format("Owner: %s", group.getOwner().getName())));

        String membersList = group.getMembers().stream()
                .filter(m -> m != group.getOwner())
                .map(VGDPlayer::getName)
                .collect(Collectors.joining(", "));

        if (!membersList.isBlank()) {
            infoMsg = infoMsg.append(Component.text(String.format("\nMembers: %s", membersList)));
        }

        return infoMsg;
    }

    @Override
    public void showUsage(Player player) {
        showUsage(player, "/vgd group list");
    }
}

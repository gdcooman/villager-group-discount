package be.glenndecooman.villagergroupdiscount.command.subcommand.group;

import be.glenndecooman.villagergroupdiscount.command.SubCommand;
import be.glenndecooman.villagergroupdiscount.model.VGDGroup;
import be.glenndecooman.villagergroupdiscount.model.VGDPlayer;
import be.glenndecooman.villagergroupdiscount.persistence.VGDGroupDAO;
import be.glenndecooman.villagergroupdiscount.persistence.VGDGroupDAOImpl;
import be.glenndecooman.villagergroupdiscount.persistence.VGDPlayerDAO;
import be.glenndecooman.villagergroupdiscount.persistence.VGDPlayerDAOImpl;
import be.glenndecooman.villagergroupdiscount.util.JPAUtil;
import be.glenndecooman.villagergroupdiscount.util.MsgUtil;
import org.bukkit.entity.Player;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import java.util.Arrays;

public class CreateGroupCommand implements SubCommand {
    @Override
    public void execute(Player sender, String[] args) {
        if (args.length > 0) {
            String groupName = parseGroupName(args);

            if (!groupName.isBlank()) {
                EntityManager em = JPAUtil.getEntityManager();
                VGDGroupDAO vgdGroupDAO = new VGDGroupDAOImpl(em);
                VGDPlayerDAO vgdPlayerDAO = new VGDPlayerDAOImpl(em);

                VGDPlayer owner = vgdPlayerDAO.findById(sender.getUniqueId());
                if (owner.getGroup() == null) {
                    if (!vgdGroupDAO.groupWithNameExists(groupName)) {
                        VGDGroup group = new VGDGroup(groupName, owner);

                        try {
                            em.getTransaction().begin();
                            vgdGroupDAO.add(group);
                            group.addMember(owner);
                            em.getTransaction().commit();
                            MsgUtil.success(sender, String.format("Congratulations, you are now the owner of %s!", groupName));
                        } catch (RollbackException e) {
                            MsgUtil.error(sender, "Sorry, something went wrong!");
                            em.getTransaction().rollback();
                        } catch (IllegalStateException e) {
                            MsgUtil.error(sender, "Sorry, something went wrong!");
                        }
                    } else {
                        MsgUtil.error(sender, "A group with that name already exists!");
                    }
                } else {
                    MsgUtil.error(sender, "You are already in a group!\nUse /vgd group leave and try again.");
                }
                em.close();
            } else {
                MsgUtil.error(sender, "You need to provide a valid name for the group!");
            }
        } else {
            showUsage(sender);
        }
    }

    private String parseGroupName(String[] args) {
        String groupName = "";

        if (args.length == 1) {
            groupName = args[0];
        } else {
            char firstChar = args[0].charAt(0);
            if (firstChar == '"' || firstChar == '\'') {
                if (args[args.length - 1].endsWith(String.valueOf(firstChar))) {
                    String joinedStr = String.join(" ", args);
                    groupName = joinedStr.substring(1, joinedStr.length() - 1);
                }
            }
        }

        return groupName;
    }

    @Override
    public void showUsage(Player player) {
        showUsage(player, "/vgd group create <GroupName|\"Group Name\">");
    }
}

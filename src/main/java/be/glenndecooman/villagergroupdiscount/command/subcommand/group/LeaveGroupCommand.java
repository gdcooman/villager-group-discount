package be.glenndecooman.villagergroupdiscount.command.subcommand.group;

import be.glenndecooman.villagergroupdiscount.command.SubCommand;
import be.glenndecooman.villagergroupdiscount.model.VGDGroup;
import be.glenndecooman.villagergroupdiscount.model.VGDPlayer;
import be.glenndecooman.villagergroupdiscount.persistence.VGDPlayerDAO;
import be.glenndecooman.villagergroupdiscount.persistence.VGDPlayerDAOImpl;
import be.glenndecooman.villagergroupdiscount.util.JPAUtil;
import be.glenndecooman.villagergroupdiscount.util.MsgUtil;
import org.bukkit.entity.Player;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

public class LeaveGroupCommand implements SubCommand {
    @Override
    public void execute(Player sender, String[] args) {
        if (args.length == 0) {
            EntityManager em = JPAUtil.getEntityManager();
            VGDPlayerDAO vgdPlayerDAO = new VGDPlayerDAOImpl(em);

            VGDPlayer player = vgdPlayerDAO.findById(sender.getUniqueId());
            if (player != null) {
                VGDGroup group = player.getGroup();
                if (group != null) {
                    if (group.getOwner() != player) {
                        try {
                            em.getTransaction().begin();
                            group.removeMember(player);
                            em.getTransaction().commit();

                            MsgUtil.success(sender, String.format("You left %s.", group.getName()));
                        } catch (RollbackException e) {
                            MsgUtil.error(sender, "Sorry, something went wrong!");
                            em.getTransaction().rollback();
                        } catch (IllegalStateException e) {
                            MsgUtil.error(sender, "Sorry, something went wrong!");
                        }
                    } else {
                        MsgUtil.error(sender, String.format("You cannot leave %s because you are the owner!\nUse /vgd group transfer <playerName> to make an other member the owner, and try again.", group.getName()));
                    }
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

    @Override
    public void showUsage(Player player) {
        showUsage(player, "/vgd group leave");
    }
}

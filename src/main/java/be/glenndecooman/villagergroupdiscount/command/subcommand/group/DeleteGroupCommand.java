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

public class DeleteGroupCommand implements SubCommand {
    @Override
    public void execute(Player sender, String[] args) {
        if (args.length == 0) {
            EntityManager em = JPAUtil.getEntityManager();
            VGDPlayerDAO vgdPlayerDAO = new VGDPlayerDAOImpl(em);
            VGDGroupDAO vgdGroupDAO = new VGDGroupDAOImpl(em);

            VGDPlayer vgdPlayer = vgdPlayerDAO.findById(sender.getUniqueId());
            VGDGroup group = vgdPlayer.getGroup();

            if (group != null) {
                if (group.getOwner() == vgdPlayer) {
                    try {
                        em.getTransaction().begin();
                        group.disband();
                        vgdGroupDAO.delete(group.getId());
                        em.getTransaction().commit();
                        MsgUtil.success(sender, String.format("Successfully deleted %s.", group.getName()));
                    } catch (RollbackException e) {
                        MsgUtil.error(sender, "Sorry, something went wrong!");
                        em.getTransaction().rollback();
                    } catch (IllegalStateException e) {
                        MsgUtil.error(sender, "Sorry, something went wrong!");
                    }
                } else {
                    MsgUtil.error(sender, String.format("Only the owner can delete %s.", group.getName()));
                }
            } else {
                MsgUtil.error(sender, "You are not in a group!");
            }
            em.close();
        } else {
            showUsage(sender);
        }
    }

    @Override
    public void showUsage(Player player) {
        showUsage(player, "/vgd group delete");
    }
}

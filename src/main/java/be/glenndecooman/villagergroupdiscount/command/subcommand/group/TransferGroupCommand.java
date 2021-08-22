package be.glenndecooman.villagergroupdiscount.command.subcommand.group;

import be.glenndecooman.villagergroupdiscount.command.SubCommand;
import be.glenndecooman.villagergroupdiscount.model.VGDGroup;
import be.glenndecooman.villagergroupdiscount.model.VGDPlayer;
import be.glenndecooman.villagergroupdiscount.persistence.VGDPlayerDAO;
import be.glenndecooman.villagergroupdiscount.persistence.VGDPlayerDAOImpl;
import be.glenndecooman.villagergroupdiscount.util.JPAUtil;
import be.glenndecooman.villagergroupdiscount.util.MsgUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

public class TransferGroupCommand implements SubCommand {
    @Override
    public void execute(Player sender, String[] args) {
        if (args.length == 1) {
            EntityManager em = JPAUtil.getEntityManager();
            VGDPlayerDAO vgdPlayerDAO = new VGDPlayerDAOImpl(em);

            VGDPlayer owner = vgdPlayerDAO.findById(sender.getUniqueId());
            if (owner != null) {
                VGDGroup group = owner.getGroup();
                if (group != null) {
                    if (group.getOwner() == owner) {
                        String playerName = args[0];
                        Player player = Bukkit.getPlayer(playerName);
                        if (player != null) {
                            VGDPlayer newOwner = vgdPlayerDAO.findById(player.getUniqueId());
                            if (newOwner != null) {
                                if (newOwner.getGroup() == group) {
                                    if (owner != newOwner) {
                                        try {
                                            em.getTransaction().begin();
                                            group.setOwner(newOwner);
                                            em.getTransaction().commit();

                                            MsgUtil.success(sender, String.format("Player %s is now the new owner of %s!", newOwner.getName(), group.getName()));
                                            MsgUtil.info(player, String.format("You are now the new owner of %s!", group.getName()));
                                        } catch (RollbackException e) {
                                            MsgUtil.error(sender, "Sorry, something went wrong!");
                                            em.getTransaction().rollback();
                                        } catch (IllegalStateException e) {
                                            MsgUtil.error(sender, "Sorry, something went wrong!");
                                        }
                                    } else {
                                        MsgUtil.warning(sender, String.format("You are already the owner of %s.", group.getName()));
                                    }
                                } else {
                                    MsgUtil.error(sender, String.format("%s is not a member of %s.", playerName, group.getName()));
                                }
                            } else {
                                MsgUtil.error(sender, "Sorry, something went wrong!");
                            }
                        } else {
                            MsgUtil.error(sender, String.format("Player %s was not found.", playerName));
                        }
                    } else {
                        MsgUtil.error(sender, String.format("Only the owner of %s can transfer the ownership.", group.getName()));
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
        showUsage(player, "/vgd group transfer <playerName>");
    }
}

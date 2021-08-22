package be.glenndecooman.villagergroupdiscount.command.subcommand.invite;

import be.glenndecooman.villagergroupdiscount.command.SubCommand;
import be.glenndecooman.villagergroupdiscount.model.VGDGroup;
import be.glenndecooman.villagergroupdiscount.model.VGDGroupInvite;
import be.glenndecooman.villagergroupdiscount.model.VGDPlayer;
import be.glenndecooman.villagergroupdiscount.persistence.VGDGroupInviteDAO;
import be.glenndecooman.villagergroupdiscount.persistence.VGDGroupInviteDAOImpl;
import be.glenndecooman.villagergroupdiscount.persistence.VGDPlayerDAO;
import be.glenndecooman.villagergroupdiscount.persistence.VGDPlayerDAOImpl;
import be.glenndecooman.villagergroupdiscount.util.JPAUtil;
import be.glenndecooman.villagergroupdiscount.util.MsgUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

public class DeclineInviteCommand implements SubCommand {
    @Override
    public void execute(Player sender, String[] args) {
        if (args.length == 1) {
            Long inviteId = parseInviteId(args[0]);

            if (inviteId != null) {
                EntityManager em = JPAUtil.getEntityManager();
                VGDPlayerDAO vgdPlayerDAO = new VGDPlayerDAOImpl(em);
                VGDGroupInviteDAO vgdGroupInviteDAO = new VGDGroupInviteDAOImpl(em);

                VGDGroupInvite invite = vgdGroupInviteDAO.findById(inviteId);
                if (invite != null) {
                    if (invite.isActive()) {
                        VGDPlayer player = vgdPlayerDAO.findById(sender.getUniqueId());
                        if (player != null) {
                            if (invite.getInvitee() == player) {
                                VGDGroup group = invite.getGroup();
                                if (group != null) {
                                    Player inviter = Bukkit.getPlayer(invite.getInviter().getId());
                                    try {
                                        em.getTransaction().begin();
                                        invite.decline();
                                        em.getTransaction().commit();

                                        MsgUtil.success(sender, String.format("You declined %s's invite.", inviter.getName()));
                                        MsgUtil.info(inviter, String.format("%s has declined your invite.", player.getName()));
                                    } catch (RollbackException e) {
                                        MsgUtil.error(sender, "Sorry, something went wrong!");
                                        em.getTransaction().rollback();
                                    } catch (IllegalStateException e ) {
                                        MsgUtil.error(sender, "Sorry, something went wrong!");
                                    }
                                } else {
                                    MsgUtil.error(sender, "Sorry, something went wrong!");
                                }
                            } else {
                                MsgUtil.error(sender, "This is not your invite.");
                            }
                        } else {
                            MsgUtil.error(sender, "Sorry, something went wrong!");
                        }
                    } else {
                        MsgUtil.warning(sender, "This invite has expired.");
                    }
                } else {
                    MsgUtil.error(sender, String.format("No invite found with ID %s.", args[0]));
                }
                em.close();
            } else {
                MsgUtil.error(sender, String.format("%s is not a valid invite ID.", args[0]));
            }
        } else {
            showUsage(sender);
        }
    }

    private Long parseInviteId(String input) {
        Long inviteId = null;

        try {
            inviteId = Long.parseLong(input);
        } catch (NumberFormatException e) {
            // The provided string is not a valid number -> inviteId will be null and an error will be thrown at the sender
        }

        return inviteId;
    }

    @Override
    public void showUsage(Player player) {
        showUsage(player, "/vgd invite decline <inviteID>");
    }
}

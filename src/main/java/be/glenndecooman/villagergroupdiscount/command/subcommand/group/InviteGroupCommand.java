package be.glenndecooman.villagergroupdiscount.command.subcommand.group;

import be.glenndecooman.villagergroupdiscount.command.SubCommand;
import be.glenndecooman.villagergroupdiscount.model.VGDGroupInvite;
import be.glenndecooman.villagergroupdiscount.model.VGDPlayer;
import be.glenndecooman.villagergroupdiscount.persistence.VGDGroupInviteDAO;
import be.glenndecooman.villagergroupdiscount.persistence.VGDGroupInviteDAOImpl;
import be.glenndecooman.villagergroupdiscount.persistence.VGDPlayerDAO;
import be.glenndecooman.villagergroupdiscount.persistence.VGDPlayerDAOImpl;
import be.glenndecooman.villagergroupdiscount.util.JPAUtil;
import be.glenndecooman.villagergroupdiscount.util.MsgUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;

public class InviteGroupCommand implements SubCommand {
    @Override
    public void execute(Player sender, String[] args) {
        if (args.length == 1) {
            EntityManager em = JPAUtil.getEntityManager();
            VGDPlayerDAO vgdPlayerDAO = new VGDPlayerDAOImpl(em);
            VGDGroupInviteDAO vgdGroupInviteDAO = new VGDGroupInviteDAOImpl(em);

            VGDPlayer inviter = vgdPlayerDAO.findById(sender.getUniqueId());
            if (inviter.getGroup() != null) {
                String playerName = args[0];
                Player player = Bukkit.getPlayer(playerName);
                if (player != null) {
                    VGDPlayer invitee = vgdPlayerDAO.findById(player.getUniqueId());
                    if (inviter != invitee) {
                        if (invitee.getGroup() == null) {
                            // Check if player has active invites pending
                            if (vgdGroupInviteDAO.findActive(invitee, inviter.getGroup()) == null) {
                                try {
                                    em.getTransaction().begin();
                                    VGDGroupInvite invite = new VGDGroupInvite(inviter, invitee, inviter.getGroup());
                                    vgdGroupInviteDAO.add(invite);
                                    em.getTransaction().commit();

                                    Component inviteMsg = makeInviteMsg(sender, inviter, invite);
                                    MsgUtil.info(player, inviteMsg);
                                    MsgUtil.info(sender, String.format("Invite sent to %s.", playerName));
                                } catch (RollbackException e) {
                                    MsgUtil.error(sender, "Sorry, something went wrong!");
                                    em.getTransaction().rollback();
                                } catch (IllegalStateException e) {
                                    MsgUtil.error(sender, "Sorry, something went wrong!");
                                }
                            } else {
                                MsgUtil.warning(sender, String.format("An active invite is already pending for %s.", player.getName()));
                            }
                        } else {
                            if (invitee.getGroup() == inviter.getGroup()) {
                                MsgUtil.error(sender, String.format("%s is already a member of %s.", invitee.getName(), inviter.getGroup().getName()));
                            } else {
                                MsgUtil.error(sender, String.format("%s is already in a group!\nTell him to use /vgd group leave and try again.", invitee.getName()));
                            }
                        }
                    } else {
                        MsgUtil.error(sender, "You can't invite yourself.");
                    }
                } else {
                    MsgUtil.error(sender, String.format("Player %s was not found.", playerName));
                }
            } else {
                MsgUtil.error(sender, "You are not in a group.");
            }
            em.close();
        } else {
            showUsage(sender);
        }
    }

    private Component makeInviteMsg(Player sender, VGDPlayer inviter, VGDGroupInvite invite) {
        return Component
                .text(String.format("%s has invited you to join %s:\n", sender.getName(), inviter.getGroup().getName()))
                .append(Component.text("Do you "))
                .append(Component.text("ACCEPT")
                        .color(TextColor.color(0x5FD745))
                        .decoration(TextDecoration.UNDERLINED, true)
                        .decoration(TextDecoration.BOLD, true)
                        .hoverEvent(Component.text(String.format("Join %s", invite.getGroup().getName())).asHoverEvent())
                        .clickEvent(ClickEvent.runCommand(String.format("/vgd invite accept %d", invite.getId()))))
                .append(Component.text(" or "))
                .append(Component.text("DECLINE")
                        .color(TextColor.color(0xFF1D1D))
                        .decoration(TextDecoration.UNDERLINED, true)
                        .decoration(TextDecoration.BOLD, true)
                        .hoverEvent(Component.text(String.format("Don't join %s", invite.getGroup().getName())).asHoverEvent())
                        .clickEvent(ClickEvent.runCommand(String.format("/vgd invite decline %d", invite.getId()))))
                .append(Component.text("?"));
    }

    @Override
    public void showUsage(Player player) {
        showUsage(player, "/vgd group invite <player>");
    }
}

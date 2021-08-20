package be.glenndecooman.villagergroupdiscount.command.subcommand.group;

import be.glenndecooman.villagergroupdiscount.command.SubCommand;
import be.glenndecooman.villagergroupdiscount.model.VGDGroupInvite;
import be.glenndecooman.villagergroupdiscount.model.VGDPlayer;
import be.glenndecooman.villagergroupdiscount.persistence.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.persistence.EntityManager;

public class InviteGroupCommand implements SubCommand {
    private VGDPlayerDAO vgdPlayerDAO;
    private VGDGroupInviteDAO vgdGroupInviteDAO;
    private EntityManager em;

    @Override
    public void execute(Player sender, String[] args) {
        if (args.length == 1) {
            String playerName = args[0];
            Player player = Bukkit.getPlayer(playerName);
            if (player != null) {
                em = JPAUtil.getEntityManager();
                vgdPlayerDAO = new VGDPlayerDAOImpl(em);
                vgdGroupInviteDAO = new VGDGroupInviteDAOImpl(em);

                VGDPlayer inviter = vgdPlayerDAO.findById(sender.getUniqueId());
                VGDPlayer invitee = vgdPlayerDAO.findById(player.getUniqueId());
                // Check if player has active invites pending
                if (vgdGroupInviteDAO.findActive(invitee, inviter.getGroup()) == null) {
                    em.getTransaction().begin();
                    VGDGroupInvite invite = new VGDGroupInvite(inviter, invitee, inviter.getGroup());
                    vgdGroupInviteDAO.add(invite);
                    em.getTransaction().commit();
                    TextComponent inviteMsg = Component
                            .text(String.format("%s has invited you to join %s:\n", sender.getName(), inviter.getGroup().getName()))
                            .append(Component.text("ACCEPT")
                                    .decoration(TextDecoration.UNDERLINED, true)
                                    .clickEvent(ClickEvent.runCommand(String.format("/vgd invite accept %d", invite.getId()))))
                            .append(Component.text(" or "))
                            .append(Component.text("DECLINE")
                                    .decoration(TextDecoration.UNDERLINED, true)
                                    .clickEvent(ClickEvent.runCommand(String.format("/vgd invite decline %d", invite.getId()))))
                            .append(Component.text("?"));
                    player.sendMessage(inviteMsg);
                } else {
                    sender.sendMessage(String.format("An active invite is already pending for %s", player.getName()));
                }
                em.close();
            } else {
                showUsage(sender, "/vgd group invite <player>");
            }
        } else {
            showUsage(sender, "/vgd group invite <player>");
        }
    }
}

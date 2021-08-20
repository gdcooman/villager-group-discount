package be.glenndecooman.villagergroupdiscount.command.subcommand.invite;

import be.glenndecooman.villagergroupdiscount.command.SubCommand;
import be.glenndecooman.villagergroupdiscount.model.VGDGroup;
import be.glenndecooman.villagergroupdiscount.model.VGDGroupInvite;
import be.glenndecooman.villagergroupdiscount.model.VGDPlayer;
import be.glenndecooman.villagergroupdiscount.persistence.*;
import org.bukkit.entity.Player;

import javax.persistence.EntityManager;

public class AcceptInviteCommand implements SubCommand {
    private VGDPlayerDAO vgdPlayerDAO;
    private VGDGroupInviteDAO vgdGroupInviteDAO;
    private EntityManager em;

    @Override
    public void execute(Player sender, String[] args) {
        if (args.length == 1) {
            Long inviteId = Long.parseLong(args[0]);
            if (inviteId != null) {
                em = JPAUtil.getEntityManager();
                vgdPlayerDAO = new VGDPlayerDAOImpl(em);
                vgdGroupInviteDAO = new VGDGroupInviteDAOImpl(em);

                VGDGroupInvite invite = vgdGroupInviteDAO.findById(inviteId);
                if (invite != null) {
                    if (invite.isActive()) {
                        VGDPlayer player = vgdPlayerDAO.findById(sender.getUniqueId());

                        if (player != null) {
                            VGDGroup group = invite.getGroup();

                            if (group != null) {
                                em.getTransaction().begin();
                                group.addMember(player);
                                invite.accept();
                                em.getTransaction().commit();
                            }
                        }
                    } else {
                        sender.sendMessage("This invite has expired.");
                    }
                } else {
                    showUsage(sender, "/vgd invite accept <inviteID>");
                }
            }
        } else {
            showUsage(sender, "/vgd invite accept <inviteID>");
        }
    }
}

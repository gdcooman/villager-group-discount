package be.glenndecooman.villagergroupdiscount.command.subcommand.group;

import be.glenndecooman.villagergroupdiscount.command.SubCommand;
import be.glenndecooman.villagergroupdiscount.model.VGDGroup;
import be.glenndecooman.villagergroupdiscount.model.VGDPlayer;
import be.glenndecooman.villagergroupdiscount.persistence.*;
import org.bukkit.entity.Player;

import javax.persistence.EntityManager;

public class LeaveGroupCommand implements SubCommand {
    @Override
    public void execute(Player sender, String[] args) {
        if (args.length == 0) {
            EntityManager em = JPAUtil.getEntityManager();
            VGDPlayerDAO vgdPlayerDAO = new VGDPlayerDAOImpl(em);
            VGDGroupDAO vgdGroupDAO = new VGDGroupDAOImpl(em);

            VGDPlayer player = vgdPlayerDAO.findById(sender.getUniqueId());
            if (player != null) {
                VGDGroup group = player.getGroup();
                if (group != null) {
                    em.getTransaction().begin();
                    group.removeMember(player);
                    em.getTransaction().commit();
                    em.close();
                }
            }
        } else {
            showUsage(sender, "/vgd group leave");
        }
    }
}

package be.glenndecooman.villagergroupdiscount.command.subcommand.group;

import be.glenndecooman.villagergroupdiscount.command.SubCommand;
import be.glenndecooman.villagergroupdiscount.model.VGDGroup;
import be.glenndecooman.villagergroupdiscount.model.VGDPlayer;
import be.glenndecooman.villagergroupdiscount.persistence.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.persistence.Entity;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

public class CreateGroupCommand implements SubCommand {
    @Override
    public void execute(Player sender, String[] args) {
        if (args.length == 1) {
            EntityManager em = JPAUtil.getEntityManager();
            VGDGroupDAO vgdGroupDAO = new VGDGroupDAOImpl(em);
            VGDPlayerDAO vgdPlayerDAO = new VGDPlayerDAOImpl(em);

            String groupName = args[0];
            if (groupName != null) {
                if (vgdGroupDAO.groupWithNameExists(groupName) == false) {
                    VGDPlayer owner = vgdPlayerDAO.findById(sender.getUniqueId());
                    VGDGroup group = new VGDGroup(groupName, owner);

                    em.getTransaction().begin();
                    vgdGroupDAO.add(group);
                    group.addMember(owner);
                    em.getTransaction().commit();
                } else {
                    sender.sendMessage("A group with that name already exists!");
                }
            }
            em.close();
        } else {
            showUsage(sender, "/vgd group create <name>");
        }
    }
}

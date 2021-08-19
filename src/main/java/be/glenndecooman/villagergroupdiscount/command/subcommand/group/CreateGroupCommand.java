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
    private VGDGroupDAO vgdGroupDAO;
    private VGDPlayerDAO vgdPlayerDAO;
    private EntityManager em;

    @Override
    public void execute(Player sender, String[] args) {
        if (args.length == 1) {
            this.em = JPAUtil.getEntityManager();
            this.vgdGroupDAO = new VGDGroupDAOImpl(this.em);
            this.vgdPlayerDAO = new VGDPlayerDAOImpl(this.em);

            String groupName = args[0];
            if (groupName != null) {
                if (vgdGroupDAO.groupWithNameExists(groupName) == false) {
                    VGDPlayer owner = vgdPlayerDAO.findById(sender.getUniqueId());
                    VGDGroup group = new VGDGroup(groupName, owner);

                    this.em.getTransaction().begin();
                    vgdGroupDAO.add(group);
                    group.addMember(owner);
                    owner.getCuredVillagers().forEach((v) -> {
                        v.setCurerGroup(group);
                    });
                    this.em.getTransaction().commit();
                } else {
                    sender.sendMessage("A group with that name already exists!");
                }
            }
            this.em.close();
        } else {
            showUsage(sender, "/vgd group create <name>");
        }
    }
}

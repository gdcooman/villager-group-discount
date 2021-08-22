package be.glenndecooman.villagergroupdiscount.task;

import be.glenndecooman.villagergroupdiscount.model.VGDCuredVillager;
import be.glenndecooman.villagergroupdiscount.model.VGDGroup;
import be.glenndecooman.villagergroupdiscount.model.VGDPlayer;
import be.glenndecooman.villagergroupdiscount.persistence.*;
import be.glenndecooman.villagergroupdiscount.util.JPAUtil;
import com.destroystokyo.paper.entity.villager.ReputationType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Villager;
import org.bukkit.scheduler.BukkitRunnable;

import javax.persistence.EntityManager;
import java.util.UUID;

public class AddCuredVillagerTask extends BukkitRunnable {
    private UUID playerId;
    private UUID villagerId;

    public AddCuredVillagerTask(UUID playerId, UUID villagerId) {
        this.playerId = playerId;
        this.villagerId = villagerId;
    }

    @Override
    public void run() {
        EntityManager em = JPAUtil.getEntityManager();
        VGDPlayerDAO vgdPlayerDAO = new VGDPlayerDAOImpl(em);
        VGDCuredVillagerDAO vgdCuredVillagerDAO = new VGDCuredVillagerDAOImpl(em);

        VGDPlayer player = vgdPlayerDAO.findById(playerId);
        VGDGroup group = player.getGroup();
        Villager villageE = (Villager) Bukkit.getEntity(villagerId);
        int reputationValue = villageE.getReputation(playerId).getReputation(ReputationType.MAJOR_POSITIVE);
        VGDCuredVillager villager = new VGDCuredVillager(villagerId, reputationValue);

        em.getTransaction().begin();
        player.addCuredVillager(villager);

        if (group != null) {
            group.addCuredVillager(villager);
        }

        vgdCuredVillagerDAO.add(villager);
        em.getTransaction().commit();
        em.close();
    }
}

package be.glenndecooman.villagergroupdiscount.task;

import be.glenndecooman.villagergroupdiscount.model.CuredVillager;
import be.glenndecooman.villagergroupdiscount.model.VGDGroup;
import be.glenndecooman.villagergroupdiscount.model.VGDPlayer;
import be.glenndecooman.villagergroupdiscount.persistence.*;
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
        CuredVillagerDAO curedVillagerDAO = new CuredVillagerDAOImpl(em);

        VGDPlayer player = vgdPlayerDAO.findById(playerId);
        VGDGroup group = player.getGroup();
        Villager villageE = (Villager) Bukkit.getEntity(villagerId);
        int reputationValue = villageE.getReputation(playerId).getReputation(ReputationType.MAJOR_POSITIVE);
        CuredVillager villager = new CuredVillager(villagerId, reputationValue);

        em.getTransaction().begin();
        curedVillagerDAO.add(villager);
        player.addCuredVillager(villager);

        if (group != null) {
            group.addCuredVillager(villager);
        }

        em.getTransaction().commit();
        em.close();
    }
}

package be.glenndecooman.villagergroupdiscount.task;

import be.glenndecooman.villagergroupdiscount.model.CuredVillager;
import be.glenndecooman.villagergroupdiscount.model.VGDGroup;
import be.glenndecooman.villagergroupdiscount.persistence.*;
import org.bukkit.scheduler.BukkitRunnable;

import javax.persistence.EntityManager;
import java.util.UUID;

public class AddCuredVillagerTask extends BukkitRunnable {
    private long groupId;
    private UUID villagerId;

    public AddCuredVillagerTask(long groupId, UUID villagerId) {
        this.groupId = groupId;
        this.villagerId = villagerId;
    }

    @Override
    public void run() {
        EntityManager em = JPAUtil.getEntityManager();
        VGDGroupDAO vgdGroupDAO = new VGDGroupDAOImpl(em);
        CuredVillagerDAO curedVillagerDAO = new CuredVillagerDAOImpl(em);
        VGDGroup group = vgdGroupDAO.findById(groupId);
        CuredVillager villager = curedVillagerDAO.findById(villagerId);

        group.addCuredVillager(villager);

        em.getTransaction().begin();
        vgdGroupDAO.update(group);
        em.getTransaction().commit();
        em.close();
    }
}

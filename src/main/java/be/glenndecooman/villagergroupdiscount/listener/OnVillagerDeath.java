package be.glenndecooman.villagergroupdiscount.listener;

import be.glenndecooman.villagergroupdiscount.persistence.VGDCuredVillagerDAO;
import be.glenndecooman.villagergroupdiscount.persistence.VGDCuredVillagerDAOImpl;
import be.glenndecooman.villagergroupdiscount.util.JPAUtil;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import javax.persistence.EntityManager;

public class OnVillagerDeath implements Listener {
    @EventHandler
    public void onVillagerDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Villager villager) {
            EntityManager em = JPAUtil.getEntityManager();
            VGDCuredVillagerDAO vgdCuredVillagerDAO = new VGDCuredVillagerDAOImpl(em);

            em.getTransaction().begin();
            vgdCuredVillagerDAO.delete(villager.getUniqueId());
            em.getTransaction().commit();
            em.close();
        }
    }
}

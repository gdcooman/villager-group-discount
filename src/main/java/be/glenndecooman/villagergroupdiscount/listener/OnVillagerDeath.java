package be.glenndecooman.villagergroupdiscount.listener;

import be.glenndecooman.villagergroupdiscount.persistence.CuredVillagerDAO;
import be.glenndecooman.villagergroupdiscount.persistence.CuredVillagerDAOImpl;
import be.glenndecooman.villagergroupdiscount.persistence.JPAUtil;
import org.bukkit.entity.Entity;
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
            CuredVillagerDAO curedVillagerDAO = new CuredVillagerDAOImpl(em);

            em.getTransaction().begin();
            curedVillagerDAO.delete(villager.getUniqueId());
            em.getTransaction().commit();
            em.close();
        }
    }
}

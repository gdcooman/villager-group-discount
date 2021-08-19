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
    private CuredVillagerDAO curedVillagerDAO;
    private EntityManager em;

    @EventHandler
    public void onVillagerDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Villager villager) {
            this.em = JPAUtil.getEntityManager();
            this.curedVillagerDAO = new CuredVillagerDAOImpl(this.em);

            this.em.getTransaction().begin();
            curedVillagerDAO.delete(villager.getUniqueId());
            this.em.getTransaction().commit();
            this.em.close();
        }
    }
}

package be.glenndecooman.villagergroupdiscount.listener;

import be.glenndecooman.villagergroupdiscount.persistence.CuredVillagerDAO;
import be.glenndecooman.villagergroupdiscount.persistence.CuredVillagerDAOImpl;
import be.glenndecooman.villagergroupdiscount.persistence.JPAUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTransformEvent;

import javax.persistence.EntityManager;
import java.util.UUID;

public class OnVillagerInfected implements Listener {
    @EventHandler
    public void onVillagerInfected(EntityTransformEvent event) {
        if (event.getTransformReason() == EntityTransformEvent.TransformReason.INFECTION) {
            UUID oldVillagerId = event.getEntity().getUniqueId();

            EntityManager em = JPAUtil.getEntityManager();
            CuredVillagerDAO curedVillagerDAO = new CuredVillagerDAOImpl(em);

            em.getTransaction().begin();
            curedVillagerDAO.delete(oldVillagerId);
            em.getTransaction().commit();
            em.close();
        }
    }
}

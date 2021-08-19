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
    private CuredVillagerDAO curedVillagerDAO;
    private EntityManager em;

    @EventHandler
    public void onVillagerInfected(EntityTransformEvent event) {
        if (event.getTransformReason() == EntityTransformEvent.TransformReason.INFECTION) {
            UUID oldVillagerId = event.getEntity().getUniqueId();

            this.em = JPAUtil.getEntityManager();
            this.curedVillagerDAO = new CuredVillagerDAOImpl(this.em);

            this.em.getTransaction().begin();
            curedVillagerDAO.delete(oldVillagerId);
            this.em.getTransaction().commit();
            this.em.close();
        }
    }
}

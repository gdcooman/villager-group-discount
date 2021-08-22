package be.glenndecooman.villagergroupdiscount.listener;

import be.glenndecooman.villagergroupdiscount.persistence.VGDCuredVillagerDAO;
import be.glenndecooman.villagergroupdiscount.persistence.VGDCuredVillagerDAOImpl;
import be.glenndecooman.villagergroupdiscount.util.JPAUtil;
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
            VGDCuredVillagerDAO vgdCuredVillagerDAO = new VGDCuredVillagerDAOImpl(em);

            em.getTransaction().begin();
            vgdCuredVillagerDAO.delete(oldVillagerId);
            em.getTransaction().commit();
            em.close();
        }
    }
}

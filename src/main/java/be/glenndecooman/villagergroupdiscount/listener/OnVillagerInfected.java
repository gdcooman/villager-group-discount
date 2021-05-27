package be.glenndecooman.villagergroupdiscount.listener;

import be.glenndecooman.villagergroupdiscount.persistence.CuredVillagerDAO;
import be.glenndecooman.villagergroupdiscount.persistence.CuredVillagerDAOImpl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTransformEvent;

import javax.persistence.EntityManager;
import java.util.UUID;

public class OnVillagerInfected implements Listener {
    private final CuredVillagerDAO curedVillagerDAO;

    public OnVillagerInfected(CuredVillagerDAO curedVillagerDAO) {
        this.curedVillagerDAO = curedVillagerDAO;
    }

    @EventHandler
    public void onVillagerInfected(EntityTransformEvent event) {
        if (event.getTransformReason() == EntityTransformEvent.TransformReason.INFECTION) {
            UUID oldVillagerId = event.getEntity().getUniqueId();
            curedVillagerDAO.delete(oldVillagerId);
        }
    }
}

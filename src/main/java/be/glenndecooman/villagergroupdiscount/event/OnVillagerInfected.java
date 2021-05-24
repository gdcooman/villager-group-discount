package be.glenndecooman.villagergroupdiscount.event;

import be.glenndecooman.villagergroupdiscount.persistence.CuredVillagerDAO;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTransformEvent;

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
            curedVillagerDAO.deleteVillager(oldVillagerId);
        }
    }
}

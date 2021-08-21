package be.glenndecooman.villagergroupdiscount.listener;

import be.glenndecooman.villagergroupdiscount.VillagerGroupDiscount;
import be.glenndecooman.villagergroupdiscount.task.AddCuredVillagerTask;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTransformEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public class OnVillagerCured implements Listener {
    private final VillagerGroupDiscount plugin;

    public OnVillagerCured(VillagerGroupDiscount plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onVillagerCured(EntityTransformEvent event) {
        if (event.getTransformReason() == EntityTransformEvent.TransformReason.CURED) {
            Villager newE = (Villager) event.getTransformedEntity();
            ZombieVillager oldE = (ZombieVillager) event.getEntity();
            UUID playerId = oldE.getConversionPlayer() != null ? oldE.getConversionPlayer().getUniqueId() : null;
            if (playerId != null) {
                    BukkitTask task = new AddCuredVillagerTask(playerId, newE.getUniqueId()).runTaskLater(plugin, 5);
            }
        }
    }
}

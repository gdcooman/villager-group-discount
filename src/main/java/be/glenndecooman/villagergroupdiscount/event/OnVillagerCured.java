package be.glenndecooman.villagergroupdiscount.event;

import be.glenndecooman.villagergroupdiscount.model.VGDGroup;
import be.glenndecooman.villagergroupdiscount.model.VGDPlayer;
import be.glenndecooman.villagergroupdiscount.persistence.VGDPlayerDAO;
import com.destroystokyo.paper.entity.villager.Reputation;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTransformEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OnVillagerCured implements Listener {
    private final VGDPlayerDAO vgdPlayerDAO;

    public OnVillagerCured(VGDPlayerDAO vgdPlayerDAO) {
        this.vgdPlayerDAO = vgdPlayerDAO;
    }
    @EventHandler
    public void onVillagerCured(EntityTransformEvent event) {
        if (event.getTransformReason() == EntityTransformEvent.TransformReason.CURED) {
            Villager newE = (Villager) event.getTransformedEntity();
            ZombieVillager oldE = (ZombieVillager) event.getEntity();
            UUID playerId = oldE.getConversionPlayer() != null ? oldE.getConversionPlayer().getUniqueId() : null;
            if (playerId != null) {
                //get player
                VGDPlayer player = vgdPlayerDAO.findPlayerById(playerId);
                //check if he's in a group
                VGDGroup curerGroup = player.getGroup();
                if (curerGroup != null) {
                    //if he is in a group: get all the players from that team and set their reputation on the villager
                    Reputation rep = newE.getReputation(playerId);
                    Map<UUID, Reputation> newReputations = new HashMap<>();
                    for (VGDPlayer member : curerGroup.getMembers()) {
                        newReputations.put(member.getId(), rep);
                    }

                    newE.setReputations(newReputations);
                }
                //if he's not: don't do anything
            }
        }
    }
}

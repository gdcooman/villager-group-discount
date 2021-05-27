package be.glenndecooman.villagergroupdiscount.listener;

import be.glenndecooman.villagergroupdiscount.model.CuredVillager;
import be.glenndecooman.villagergroupdiscount.model.VGDGroup;
import be.glenndecooman.villagergroupdiscount.model.VGDPlayer;
import be.glenndecooman.villagergroupdiscount.persistence.CuredVillagerDAOImpl;
import be.glenndecooman.villagergroupdiscount.persistence.VGDPlayerDAO;
import be.glenndecooman.villagergroupdiscount.persistence.VGDPlayerDAOImpl;
import com.destroystokyo.paper.entity.villager.Reputation;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTransformEvent;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OnVillagerCured implements Listener {
    private final VGDPlayerDAO vgdPlayerDAO;
    private final CuredVillagerDAOImpl curedVillagerDAO;

    public OnVillagerCured(VGDPlayerDAOImpl vgdPlayerDAO, CuredVillagerDAOImpl curedVillagerDAO) {
        this.vgdPlayerDAO = vgdPlayerDAO;
        this.curedVillagerDAO = curedVillagerDAO;
    }

    @EventHandler
    public void onVillagerCured(EntityTransformEvent event) {
        if (event.getTransformReason() == EntityTransformEvent.TransformReason.CURED) {
            Villager newE = (Villager) event.getTransformedEntity();
            ZombieVillager oldE = (ZombieVillager) event.getEntity();
            UUID playerId = oldE.getConversionPlayer() != null ? oldE.getConversionPlayer().getUniqueId() : null;
            if (playerId != null) {
                //get player
                VGDPlayer player = vgdPlayerDAO.findById(playerId);
                CuredVillager villager = new CuredVillager(newE.getUniqueId(), player);
                //check if he's in a group
                VGDGroup curerGroup = player.getGroup();
                if (curerGroup != null) {
                    //if he is in a group: get all the players from that team and set their reputation on the villager
                    villager.setCurerGroup(curerGroup);
                    Reputation rep = newE.getReputation(playerId);
                    Map<UUID, Reputation> newReputations = new HashMap<>();
                    for (VGDPlayer member : curerGroup.getMembers()) {
                        newReputations.put(member.getId(), rep);
                    }

                    newE.setReputations(newReputations);
                }
                this.curedVillagerDAO.add(villager);
                //if he's not: don't do anything
            }
        }
    }
}

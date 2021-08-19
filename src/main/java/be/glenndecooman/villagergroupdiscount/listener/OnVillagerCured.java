package be.glenndecooman.villagergroupdiscount.listener;

import be.glenndecooman.villagergroupdiscount.model.CuredVillager;
import be.glenndecooman.villagergroupdiscount.model.VGDGroup;
import be.glenndecooman.villagergroupdiscount.model.VGDPlayer;
import be.glenndecooman.villagergroupdiscount.persistence.CuredVillagerDAOImpl;
import be.glenndecooman.villagergroupdiscount.persistence.JPAUtil;
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
    private VGDPlayerDAO vgdPlayerDAO;
    private CuredVillagerDAOImpl curedVillagerDAO;
    private EntityManager em;

    @EventHandler
    public void onVillagerCured(EntityTransformEvent event) {
        if (event.getTransformReason() == EntityTransformEvent.TransformReason.CURED) {
            Villager newE = (Villager) event.getTransformedEntity();
            ZombieVillager oldE = (ZombieVillager) event.getEntity();
            UUID playerId = oldE.getConversionPlayer() != null ? oldE.getConversionPlayer().getUniqueId() : null;
            if (playerId != null) {
                this.em = JPAUtil.getEntityManager();
                this.vgdPlayerDAO = new VGDPlayerDAOImpl(this.em);
                this.curedVillagerDAO = new CuredVillagerDAOImpl(this.em);

                //get player
                VGDPlayer player = vgdPlayerDAO.findById(playerId);
                CuredVillager villager = new CuredVillager(newE.getUniqueId());
                this.em.getTransaction().begin();
                curedVillagerDAO.add(villager);
                player.addCuredVillager(villager);
                //check if he's in a group
                VGDGroup curerGroup = player.getGroup();
                if (curerGroup != null) {
                    //if he is in a group: get all the players from that team and set their reputation on the villager
                    curerGroup.addCuredVillager(villager);
                    Reputation rep = newE.getReputation(playerId);
                    Map<UUID, Reputation> newReputations = new HashMap<>();
                    for (VGDPlayer member : curerGroup.getMembers()) {
                        newReputations.put(member.getId(), rep);
                    }

                    newE.setReputations(newReputations);
                }
                this.em.getTransaction().commit();
                //if he's not: don't do anything
                this.em.close();
            }
        }
    }
}

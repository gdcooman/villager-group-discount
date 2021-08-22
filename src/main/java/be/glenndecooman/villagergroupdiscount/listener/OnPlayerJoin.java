package be.glenndecooman.villagergroupdiscount.listener;

import be.glenndecooman.villagergroupdiscount.model.VGDPlayer;
import be.glenndecooman.villagergroupdiscount.util.JPAUtil;
import be.glenndecooman.villagergroupdiscount.persistence.VGDPlayerDAO;
import be.glenndecooman.villagergroupdiscount.persistence.VGDPlayerDAOImpl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.persistence.EntityManager;
import java.util.UUID;

public class OnPlayerJoin implements Listener {
    @EventHandler
    public void onPlayerPreLogin(PlayerJoinEvent event) {
        EntityManager em = JPAUtil.getEntityManager();
        VGDPlayerDAO vgdPlayerDAO = new VGDPlayerDAOImpl(em);

        UUID playerId = event.getPlayer().getUniqueId();


        VGDPlayer player = vgdPlayerDAO.findById(playerId);

        if (player  == null) {
            em.getTransaction().begin();
            vgdPlayerDAO.add(new VGDPlayer(playerId));
            em.getTransaction().commit();
            em.close();
        }
    }
}

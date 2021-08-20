package be.glenndecooman.villagergroupdiscount.listener;

import be.glenndecooman.villagergroupdiscount.model.VGDPlayer;
import be.glenndecooman.villagergroupdiscount.persistence.JPAUtil;
import be.glenndecooman.villagergroupdiscount.persistence.VGDPlayerDAO;
import be.glenndecooman.villagergroupdiscount.persistence.VGDPlayerDAOImpl;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import javax.persistence.EntityManager;
import java.util.UUID;
import java.util.logging.Logger;

public class OnPlayerPreLogin implements Listener {
    @EventHandler
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        EntityManager em = JPAUtil.getEntityManager();
        VGDPlayerDAO vgdPlayerDAO = new VGDPlayerDAOImpl(em);

        UUID playerId = event.getUniqueId();

        VGDPlayer player = vgdPlayerDAO.findById(playerId);

        if (player == null) {
            em.getTransaction().begin();
            vgdPlayerDAO.add(new VGDPlayer(playerId));
            em.getTransaction().commit();
            em.close();
        }
    }
}

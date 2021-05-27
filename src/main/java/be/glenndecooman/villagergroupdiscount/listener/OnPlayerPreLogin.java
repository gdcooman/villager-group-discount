package be.glenndecooman.villagergroupdiscount.listener;

import be.glenndecooman.villagergroupdiscount.model.VGDPlayer;
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
    private final VGDPlayerDAO vgdPlayerDAO;

    public OnPlayerPreLogin(VGDPlayerDAO vgdPlayerDAO) {
        this.vgdPlayerDAO = vgdPlayerDAO;
    }

    @EventHandler
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        UUID playerId = event.getUniqueId();

        VGDPlayer player = vgdPlayerDAO.findById(playerId);

        Logger logger = Bukkit.getLogger();
        if (player == null) {
            vgdPlayerDAO.add(new VGDPlayer(playerId));
            logger.info("in methodeken");
        } else {
            logger.info("uit methodeken");
        }
    }
}

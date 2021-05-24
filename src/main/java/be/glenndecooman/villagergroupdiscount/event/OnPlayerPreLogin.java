package be.glenndecooman.villagergroupdiscount.event;

import be.glenndecooman.villagergroupdiscount.model.VGDPlayer;
import be.glenndecooman.villagergroupdiscount.persistence.VGDPlayerDAO;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;

public class OnPlayerPreLogin implements Listener {
    VGDPlayerDAO vgdPlayerDAO;

    @EventHandler
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        UUID playerId = event.getUniqueId();

        VGDPlayer player = vgdPlayerDAO.findPlayerById(playerId);

        if (player == null) {
            vgdPlayerDAO.addPlayer(new VGDPlayer(playerId));
        }
    }
}

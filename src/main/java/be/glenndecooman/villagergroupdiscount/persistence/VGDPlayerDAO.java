package be.glenndecooman.villagergroupdiscount.persistence;

import be.glenndecooman.villagergroupdiscount.model.VGDPlayer;

import java.util.UUID;

public interface VGDPlayerDAO {
    VGDPlayer findPlayerById(UUID id);
    VGDPlayer addPlayer(VGDPlayer player);
    VGDPlayer deletePlayer(UUID id);
    VGDPlayer updatePlayer(VGDPlayer player);
}

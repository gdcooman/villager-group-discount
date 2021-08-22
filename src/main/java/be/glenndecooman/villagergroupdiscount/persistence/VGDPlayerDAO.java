package be.glenndecooman.villagergroupdiscount.persistence;

import be.glenndecooman.villagergroupdiscount.model.VGDPlayer;

import java.util.UUID;

public interface VGDPlayerDAO {
    VGDPlayer findById(UUID id);
    void add(VGDPlayer player);
    void delete(UUID id);
    VGDPlayer update(VGDPlayer player);
}

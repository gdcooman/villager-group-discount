package be.glenndecooman.villagergroupdiscount.persistence;

import be.glenndecooman.villagergroupdiscount.model.VGDCuredVillager;

import java.util.UUID;

public interface VGDCuredVillagerDAO {
    VGDCuredVillager findById(UUID id);
    void add(VGDCuredVillager villager);
    void delete(UUID id);
    VGDCuredVillager update(VGDCuredVillager villager);
}

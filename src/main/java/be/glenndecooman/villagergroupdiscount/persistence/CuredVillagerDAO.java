package be.glenndecooman.villagergroupdiscount.persistence;

import be.glenndecooman.villagergroupdiscount.model.CuredVillager;

import java.util.UUID;

public interface CuredVillagerDAO {
    CuredVillager findById(UUID id);
    void add(CuredVillager villager);
    void delete(UUID id);
    CuredVillager update(CuredVillager villager);
}

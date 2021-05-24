package be.glenndecooman.villagergroupdiscount.persistence;

import be.glenndecooman.villagergroupdiscount.model.CuredVillager;

import java.util.UUID;

public interface CuredVillagerDAO {
    CuredVillager findVillagerById(UUID id);
    CuredVillager addVillager(CuredVillager villager);
    CuredVillager deleteVillager(UUID id);
    CuredVillager updateVillager(CuredVillager villager);
}

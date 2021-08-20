package be.glenndecooman.villagergroupdiscount.persistence;

import be.glenndecooman.villagergroupdiscount.model.CuredVillager;

import javax.persistence.EntityManager;
import java.util.UUID;

public class CuredVillagerDAOImpl implements CuredVillagerDAO {
    private final EntityManager em;

    public CuredVillagerDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public CuredVillager findById(UUID id) {
        return em.find(CuredVillager.class, id);
    }

    @Override
    public void add(CuredVillager villager) {
        em.persist(villager);
    }

    @Override
    public void delete(UUID id) {
        CuredVillager villager = findById(id);
        if (villager != null) {
            em.remove(villager);
        }
    }

    @Override
    public CuredVillager update(CuredVillager villager) {
        return em.merge(villager);
    }
}

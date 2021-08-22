package be.glenndecooman.villagergroupdiscount.persistence;

import be.glenndecooman.villagergroupdiscount.model.VGDCuredVillager;

import javax.persistence.EntityManager;
import java.util.UUID;

public class VGDCuredVillagerDAOImpl implements VGDCuredVillagerDAO {
    private final EntityManager em;

    public VGDCuredVillagerDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public VGDCuredVillager findById(UUID id) {
        return em.find(VGDCuredVillager.class, id);
    }

    @Override
    public void add(VGDCuredVillager villager) {
        em.persist(villager);
    }

    @Override
    public void delete(UUID id) {
        VGDCuredVillager villager = findById(id);
        if (villager != null) {
            em.remove(villager);
        }
    }

    @Override
    public VGDCuredVillager update(VGDCuredVillager villager) {
        return em.merge(villager);
    }
}

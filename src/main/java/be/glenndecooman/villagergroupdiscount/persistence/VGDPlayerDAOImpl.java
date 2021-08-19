package be.glenndecooman.villagergroupdiscount.persistence;

import be.glenndecooman.villagergroupdiscount.model.VGDPlayer;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

public class VGDPlayerDAOImpl implements VGDPlayerDAO {

    EntityManager em;

    public VGDPlayerDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public VGDPlayer findById(UUID id) {
        return em.find(VGDPlayer.class, id);
    }

    @Override
    public void add(VGDPlayer player) {
        em.persist(player);
    }

    @Override
    public void delete(UUID id) {
        VGDPlayer player = findById(id);
        if (player != null) {
            em.remove(player);
        }
    }

    @Override
    public VGDPlayer update(VGDPlayer player) {
        return em.merge(player);
    }
}

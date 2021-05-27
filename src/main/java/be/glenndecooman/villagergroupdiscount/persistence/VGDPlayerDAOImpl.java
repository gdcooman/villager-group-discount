package be.glenndecooman.villagergroupdiscount.persistence;

import be.glenndecooman.villagergroupdiscount.model.VGDGroup;
import be.glenndecooman.villagergroupdiscount.model.VGDPlayer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.UUID;

public class VGDPlayerDAOImpl implements VGDPlayerDAO {

    @PersistenceContext
    EntityManager em;

    public VGDPlayerDAOImpl() {
        this.em = JPAUtil.getEntityManager();
    }

    @Override
    public VGDPlayer findById(UUID id) {
        return em.find(VGDPlayer.class, id);
    }

    @Override
    public void add(VGDPlayer player) {
        em.getTransaction().begin();
        em.persist(player);
        em.getTransaction().commit();
    }

    @Override
    public void delete(UUID id) {
        em.getTransaction().begin();
        em.remove(em.getReference(VGDPlayer.class, id));
        em.getTransaction().commit();
    }

    @Override
    public VGDPlayer update(VGDPlayer player) {
        return em.merge(player);
    }
}

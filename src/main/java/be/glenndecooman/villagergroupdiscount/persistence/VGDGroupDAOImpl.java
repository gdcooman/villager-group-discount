package be.glenndecooman.villagergroupdiscount.persistence;

import be.glenndecooman.villagergroupdiscount.model.VGDGroup;
import be.glenndecooman.villagergroupdiscount.model.VGDPlayer;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class VGDGroupDAOImpl implements VGDGroupDAO {
    private final EntityManager em;

    public VGDGroupDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public VGDGroup findById(Long id) {
        return em.find(VGDGroup.class, id);
    }

    @Override
    public boolean groupWithNameExists(String name) {
        return (Long) em.createQuery(
                "SELECT COUNT(g) FROM VGDGroup g WHERE g.name = :groupName"
        ).setParameter("groupName", name).getSingleResult() == 1L;
    }

    @Override
    public void add(VGDGroup group) throws EntityExistsException {
        em.persist(group);
    }

    @Override
    public void delete(Long id) {
        VGDGroup group = findById(id);
        if (group != null) {
            em.remove(group);
        }
    }

    @Override
    public VGDGroup update(VGDGroup group) {
        return em.merge(group);
    }
}

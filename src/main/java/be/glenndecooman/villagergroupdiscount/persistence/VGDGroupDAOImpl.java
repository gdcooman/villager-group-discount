package be.glenndecooman.villagergroupdiscount.persistence;

import be.glenndecooman.villagergroupdiscount.model.VGDGroup;

import javax.persistence.EntityManager;

public class VGDGroupDAOImpl implements VGDGroupDAO {
    private EntityManager em;

    public VGDGroupDAOImpl() {
        this.em = JPAUtil.getEntityManager();
    }

    @Override
    public VGDGroup findById(Long id) {
        return em.find(VGDGroup.class, id);
    }

    @Override
    public void add(VGDGroup group) {
        em.getTransaction().begin();
        em.persist(group);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Long id) {
        em.getTransaction().begin();
        em.remove(em.getReference(VGDGroup.class, id));
        em.getTransaction().commit();
    }

    @Override
    public VGDGroup update(VGDGroup group) {
        return em.merge(group);
    }
}

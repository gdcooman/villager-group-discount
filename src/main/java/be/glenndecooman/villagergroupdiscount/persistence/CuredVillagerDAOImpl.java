package be.glenndecooman.villagergroupdiscount.persistence;

import be.glenndecooman.villagergroupdiscount.model.CuredVillager;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.UUID;

public class CuredVillagerDAOImpl implements CuredVillagerDAO {
    private EntityManager em;

    public CuredVillagerDAOImpl() {
        this.em = JPAUtil.getEntityManager();
    }

    @Override
    public CuredVillager findById(UUID id) {
        return em.find(CuredVillager.class, id);
    }

    @Override
    public void add(CuredVillager villager) {
        em.getTransaction().begin();
        em.persist(villager);
        em.getTransaction().commit();
    }

    @Override
    public void delete(UUID id) {
        em.getTransaction().begin();
        em.remove(em.getReference(CuredVillager.class, id));
        em.getTransaction().commit();
    }

    @Override
    public CuredVillager update(CuredVillager villager) {
        return em.merge(villager);
    }
}

package be.glenndecooman.villagergroupdiscount.persistence;

import be.glenndecooman.villagergroupdiscount.model.VGDGroup;
import be.glenndecooman.villagergroupdiscount.model.VGDGroupInvite;
import be.glenndecooman.villagergroupdiscount.model.VGDPlayer;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.time.LocalDateTime;

public class VGDGroupInviteDAOImpl implements VGDGroupInviteDAO {
    private final EntityManager em;

    public VGDGroupInviteDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public VGDGroupInvite findActive(VGDPlayer invitee, VGDGroup group) {
        VGDGroupInvite invite;
        try {
            invite = (VGDGroupInvite) em.createNativeQuery(
                            "SELECT * " +
                                    "FROM VGDGROUPINVITE " +
                                    "WHERE inviteeId = :inviteeId AND groupId = :groupId AND (accepted = FALSE AND declined = FALSE AND DATEADD(MINUTE, minutesTillExpires, createdAt) > CURRENT_TIMESTAMP())"
                    ).setParameter("inviteeId", invitee.getId())
                     .setParameter("groupId", group.getId())
                     .getSingleResult();
        } catch (NoResultException e) {
            //There is no active invite
            invite = null;
        }

        return invite;
    }

    @Override
    public VGDGroupInvite findById(long id) {
        return em.find(VGDGroupInvite.class, id);
    }

    @Override
    public void add(VGDGroupInvite invite) {
        em.persist(invite);
    }
}

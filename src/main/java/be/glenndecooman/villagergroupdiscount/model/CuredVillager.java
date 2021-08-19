package be.glenndecooman.villagergroupdiscount.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity(name = "CuredVillager")
public class CuredVillager {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "PlayerUUID")
    private VGDPlayer curer;
    @ManyToOne
    @JoinColumn(name = "GroupId")
    private VGDGroup curerGroup;

    public CuredVillager(UUID id, VGDPlayer curer, VGDGroup curerGroup) {
        this.id = id;
        this.curer = curer;
        this.curerGroup = curerGroup;
    }

    public CuredVillager(UUID id) {
        this(id, null, null);
    }

    // JPA/Hibernate
    protected CuredVillager() {}

    public UUID getId() {
        return this.id;
    }

    public void setCurer(VGDPlayer player) {
        this.curer = player;
    }

    public VGDPlayer getCurer() {
        return this.curer;
    }

    public void setCurerGroup(VGDGroup group) {
        this.curerGroup = group;
    }

    public VGDGroup getCurerGroup() {
        return this.curerGroup;
    }
}

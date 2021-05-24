package be.glenndecooman.villagergroupdiscount.model;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "VGDPlayer")
public class VGDPlayer {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "GroupId")
    private VGDGroup vgdGroup;
    @OneToMany(mappedBy = "curer", cascade = CascadeType.ALL)
    private Set<CuredVillager> curedVillagers;

    public VGDPlayer(UUID id, VGDGroup vgdGroup, Set<CuredVillager> curedVillagers) {
        this.id = id;
        this.vgdGroup = vgdGroup;
        this.curedVillagers = curedVillagers;
    }

    public VGDPlayer(UUID id, VGDGroup vgdGroup) {
        this(id, vgdGroup, new HashSet<>());
    }

    public VGDPlayer(UUID id) {
        this(id, null);
    }

    // JPA/Hibernate
    protected VGDPlayer() {}

    public UUID getId() {
        return this.id;
    }

    public void setGroup(VGDGroup group) {
        this.vgdGroup = group;
    }

    public VGDGroup getGroup() {
        return this.vgdGroup;
    }

    public Set<CuredVillager> getCuredVillagers() {
        return Collections.unmodifiableSet(this.curedVillagers);
    }

    public void addCuredVillager(CuredVillager villager) {
        this.curedVillagers.add(villager);
    }
}

package be.glenndecooman.villagergroupdiscount.model;

import org.bukkit.Bukkit;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "VGDPlayer")
public class VGDPlayer {
    @Id
    private UUID id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "groupId")
    private VGDGroup vgdGroup;
    @OneToMany(mappedBy = "curer", cascade = CascadeType.ALL)
    private Set<VGDCuredVillager> curedVillagers;

    public VGDPlayer(UUID id, VGDGroup vgdGroup, Set<VGDCuredVillager> curedVillagers) {
        this.id = id;
        this.name = Bukkit.getPlayer(id).getName();
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

    public String getName() {
        return this.name;
    }

    public void setGroup(VGDGroup group) {
        this.vgdGroup = group;
    }

    public VGDGroup getGroup() {
        return this.vgdGroup;
    }

    public Set<VGDCuredVillager> getCuredVillagers() {
        return Collections.unmodifiableSet(this.curedVillagers);
    }

    public void addCuredVillager(VGDCuredVillager villager) {
        this.curedVillagers.add(villager);
        villager.setCurer(this);
    }

    public void removeCuredVillager(VGDCuredVillager villager) {
        this.curedVillagers.remove(villager);
        villager.setCurer(null);
    }
}

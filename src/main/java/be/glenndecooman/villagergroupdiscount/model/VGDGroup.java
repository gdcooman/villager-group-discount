package be.glenndecooman.villagergroupdiscount.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "VGDGroup")
public class VGDGroup {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @OneToOne
    @JoinColumn(name = "OwnerUUID")
    private VGDPlayer owner;
    @OneToMany(mappedBy = "vgdGroup", cascade = CascadeType.ALL)
    private Set<VGDPlayer> members;
    @OneToMany(mappedBy = "curerGroup", cascade = CascadeType.ALL)
    private Set<CuredVillager> curedVillagers;

    public VGDGroup(String name, VGDPlayer owner, Set<VGDPlayer> members, Set<CuredVillager> curedVillagers) {
        this.name = name;
        this.owner = owner;
        this.members = members;
        this.curedVillagers = curedVillagers;
    }

    public VGDGroup(String name, VGDPlayer owner) {
        this(name, owner, new HashSet<>(), new HashSet<>());
    }

    // JPA/Hibernate
    protected VGDGroup() {}

    public Long getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setOwner(VGDPlayer player) {
        this.owner = player;
    }

    public VGDPlayer getOwner() {
        return this.owner;
    }

    public Set<VGDPlayer> getMembers() {
        return Collections.unmodifiableSet(members);
    }

    public Set<CuredVillager> getCuredVillagers() {
        return Collections.unmodifiableSet(curedVillagers);
    }

    public void addMember(VGDPlayer player) {
        this.members.add(player);
        player.setGroup(this);
    }

    public void removeMember(VGDPlayer player) {
        this.members.remove(player);
        player.setGroup(null);
    }

    public void addCuredVillager(CuredVillager villager) {
        this.curedVillagers.add(villager);
        villager.setCurerGroup(this);
    }

    public void removeCuredVillager(CuredVillager villager) {
        this.curedVillagers.remove(villager);
        villager.setCurerGroup(null);
    }
}

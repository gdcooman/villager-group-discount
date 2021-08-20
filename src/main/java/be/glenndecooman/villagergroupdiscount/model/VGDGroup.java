package be.glenndecooman.villagergroupdiscount.model;

import com.destroystokyo.paper.entity.villager.Reputation;
import com.destroystokyo.paper.entity.villager.ReputationType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Villager;

import javax.persistence.*;
import java.util.*;

@Entity(name = "VGDGroup")
public class VGDGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @OneToOne
    @JoinColumn(name = "ownerUUID")
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
        player.getCuredVillagers().forEach((v) -> {
            addCuredVillager(v);
        });
    }

    public void removeMember(VGDPlayer player) {
        this.members.remove(player);

        player.setGroup(null);
        player.getCuredVillagers().forEach((v) -> {
            removeCuredVillager(v);
        });
    }

    public void addCuredVillager(CuredVillager villager) {
        this.curedVillagers.add(villager);
        villager.setCurerGroup(this);
        addMajorPositiveReputation(villager);
    }

    public void removeCuredVillager(CuredVillager villager) {
        this.curedVillagers.remove(villager);
        villager.setCurerGroup(null);
    }

    private void addMajorPositiveReputation(CuredVillager vgdVillager) {
        // Add MAJOR_POSITIVE reputation for all group members to the new CuredVillager
        Villager villager = (Villager) Bukkit.getEntity(vgdVillager.getId());
        if (villager != null) {
            int repValue = villager.getReputation(vgdVillager.getCurer().getId()).getReputation(ReputationType.MAJOR_POSITIVE);

            for (VGDPlayer member : members) {
                Reputation rep = villager.getReputation(member.getId());
                rep.setReputation(ReputationType.MAJOR_POSITIVE, repValue);
                villager.setReputation(member.getId(), rep);
            }
        }
    }
}

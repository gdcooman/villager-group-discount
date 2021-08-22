package be.glenndecooman.villagergroupdiscount.model;

import be.glenndecooman.villagergroupdiscount.util.MsgUtil;
import com.destroystokyo.paper.entity.villager.ReputationType;
import org.bukkit.Bukkit;

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
    @JoinColumn(name = "owner")
    private VGDPlayer owner;
    @OneToMany(mappedBy = "vgdGroup")
    private Set<VGDPlayer> members;
    @OneToMany(mappedBy = "curerGroup")
    private Set<VGDCuredVillager> curedVillagers;

    public VGDGroup(String name, VGDPlayer owner, Set<VGDPlayer> members, Set<VGDCuredVillager> curedVillagers) {
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

    public Set<VGDCuredVillager> getCuredVillagers() {
        return Collections.unmodifiableSet(curedVillagers);
    }

    public void addMember(VGDPlayer player) {
        player.setGroup(this);
        player.getCuredVillagers().forEach(this::addCuredVillager);

        curedVillagers.forEach((v) -> {
            v.setReputation(player, ReputationType.MAJOR_POSITIVE);
        });

        this.members.add(player);
    }

    public void removeMember(VGDPlayer player) {
        player.setGroup(null);

        player.getCuredVillagers().forEach(this::removeCuredVillager);

        curedVillagers.forEach((v) -> {
            v.setReputation(player, ReputationType.MAJOR_POSITIVE, 0);
        });

        this.members.remove(player);
    }

    public void addCuredVillager(VGDCuredVillager villager) {
        this.curedVillagers.add(villager);
        villager.setCurerGroup(this);

        members.forEach(m -> {
            villager.setReputation(m, ReputationType.MAJOR_POSITIVE);
        });
    }

    public void removeCuredVillager(VGDCuredVillager villager) {
        this.curedVillagers.remove(villager);
        villager.setCurerGroup(null);

        members.forEach(m -> {
            villager.setReputation(m, ReputationType.MAJOR_POSITIVE, 0);
        });
    }

    public void disband() {
        Object[] membersArr = members.toArray();
        for(int i = 0; i < membersArr.length; i++) {
            VGDPlayer member = (VGDPlayer) membersArr[i];
            removeMember(member);
            MsgUtil.info(Bukkit.getPlayer(member.getId()), String.format("%s has been disbanded.", name));
        }
    }
}

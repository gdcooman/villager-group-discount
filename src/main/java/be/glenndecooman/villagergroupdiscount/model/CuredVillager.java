package be.glenndecooman.villagergroupdiscount.model;

import com.destroystokyo.paper.entity.villager.Reputation;
import com.destroystokyo.paper.entity.villager.ReputationType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Villager;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Map;
import java.util.UUID;

@Entity(name = "CuredVillager")
public class CuredVillager {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "playerUUID")
    private VGDPlayer curer;
    @ManyToOne
    @JoinColumn(name = "groupId")
    private VGDGroup curerGroup;
    private int reputationValue;

    public CuredVillager(UUID id, VGDPlayer curer, VGDGroup curerGroup, int reputationValue) {
        this.id = id;
        this.curer = curer;
        this.curerGroup = curerGroup;
        this.reputationValue = reputationValue;
    }

    public CuredVillager(UUID id, int reputationValue) {
        this(id, null, null, reputationValue);
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

    public void setReputationValue(int reputationValue) {
        this.reputationValue = reputationValue;
    }

    public int getReputationValue() {
        return this.reputationValue;
    }

    public void setReputation(VGDPlayer player, ReputationType repType) {
        setReputation(player, repType, reputationValue);
    }

    public void setReputation(VGDPlayer player, ReputationType repType, int reputationValue) {
        Villager villager = (Villager) Bukkit.getEntity(id);

        if (villager != null) {
            if (player != curer) {
                Map<UUID, Reputation> reputations = villager.getReputations();
                Reputation rep = reputations.get(player.getId());
                rep.setReputation(ReputationType.MAJOR_POSITIVE, reputationValue);
                reputations.put(player.getId(), rep);
                // You need to clear them first, because it won't change non-zero reps to 0 (probably a bug).
                villager.clearReputations();
                villager.setReputations(reputations);
            }
        }
    }
}

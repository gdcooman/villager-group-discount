package be.glenndecooman.villagergroupdiscount.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "VGDGroupInvite")
public class VGDGroupInvite {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "playerId")
    private VGDPlayer player;
    @ManyToOne
    @JoinColumn(name = "groupId")
    private VGDGroup group;
    private boolean accepted;
    private boolean declined;
    private boolean active;
    private LocalDateTime expiresAt;

    public VGDGroupInvite(VGDPlayer player, VGDGroup group, long minutesTillExpires) {
        this.player = player;
        this.group = group;
        this.accepted = false;
        this.declined = false;
        this.active = false;
        this.expiresAt = LocalDateTime.now().plusMinutes(minutesTillExpires);
    }

    public VGDGroupInvite(VGDPlayer player, VGDGroup group) {
        this(player, group, 15);
    }

    // JPA/Hibernate
    protected VGDGroupInvite() {}

    public Long getId() {
        return this.id;
    }

    public void setPlayer(VGDPlayer player) {
        this.player = player;
    }

    public VGDPlayer getPlayer() {
        return this.player;
    }

    public void setGroup(VGDGroup group) {
        this.group = group;
    }

    public VGDGroup getGroup() {
        return this.group;
    }

    public void accept() {
        if (this.isActive()) {
            this.accepted = true;
        }
    }

    public boolean isAccepted() {
        return this.accepted;
    }

    public void decline() {
        if (this.isActive()) {
            this.declined = true;
        }
    }

    public boolean isDeclined() {
        return this.declined;
    }

    public boolean isActive() {
        return (this.isExpired() == false && (this.accepted == false && this.declined == false));
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiresAt);
    }
}

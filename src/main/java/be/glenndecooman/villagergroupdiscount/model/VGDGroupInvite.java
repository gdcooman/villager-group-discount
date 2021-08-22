package be.glenndecooman.villagergroupdiscount.model;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "VGDGroupInvite")
public class VGDGroupInvite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "inviterId")
    private VGDPlayer inviter;
    @ManyToOne
    @JoinColumn(name = "inviteeId")
    private VGDPlayer invitee;
    @ManyToOne
    @JoinColumn(name = "groupId")
    private VGDGroup group;
    private boolean accepted;
    private boolean declined;
    private LocalDateTime createdAt;
    private long minutesTillExpires;

    public VGDGroupInvite(VGDPlayer inviter, VGDPlayer invitee, VGDGroup group, long minutesTillExpires) {
        this.inviter = inviter;
        this.invitee = invitee;
        this.group = group;
        this.accepted = false;
        this.declined = false;
        this.createdAt = LocalDateTime.now();
        this.minutesTillExpires = minutesTillExpires;
    }

    public VGDGroupInvite(VGDPlayer inviter, VGDPlayer invitee, VGDGroup group) {
        this(inviter, invitee, group, 15);
    }

    // JPA/Hibernate
    protected VGDGroupInvite() {}

    public Long getId() {
        return this.id;
    }

    public void setInviter(VGDPlayer inviter) {
        this.inviter = inviter;
    }

    public VGDPlayer getInviter() {
        return this.inviter;
    }

    public void setInvitee(VGDPlayer invitee) {
        this.invitee = invitee;
    }

    public VGDPlayer getInvitee() {
        return this.invitee;
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
        return LocalDateTime.now().isAfter(this.createdAt.plusMinutes(this.minutesTillExpires));
    }
}

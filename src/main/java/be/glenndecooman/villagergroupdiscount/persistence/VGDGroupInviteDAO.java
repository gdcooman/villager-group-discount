package be.glenndecooman.villagergroupdiscount.persistence;

import be.glenndecooman.villagergroupdiscount.model.VGDGroup;
import be.glenndecooman.villagergroupdiscount.model.VGDGroupInvite;
import be.glenndecooman.villagergroupdiscount.model.VGDPlayer;

public interface VGDGroupInviteDAO {
    VGDGroupInvite findActive(VGDPlayer player, VGDGroup group);
    VGDGroupInvite findById(long id);
    void add(VGDGroupInvite invite);
}

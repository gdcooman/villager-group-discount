package be.glenndecooman.villagergroupdiscount.persistence;

import be.glenndecooman.villagergroupdiscount.model.VGDGroup;

public interface VGDGroupDAO {
    VGDGroup findGroupById(int id);
    VGDGroup addGroup(VGDGroup group);
    VGDGroup deleteGroup(int id);
    VGDGroup updateGroup(VGDGroup group);
}

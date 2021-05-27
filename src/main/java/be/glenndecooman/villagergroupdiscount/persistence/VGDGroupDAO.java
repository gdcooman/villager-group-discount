package be.glenndecooman.villagergroupdiscount.persistence;

import be.glenndecooman.villagergroupdiscount.model.VGDGroup;

public interface VGDGroupDAO {
    VGDGroup findById(Long id);
    void add(VGDGroup group);
    void delete(Long id);
    VGDGroup update(VGDGroup group);
}

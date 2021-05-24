package be.glenndecooman.villagergroupdiscount.model;

import java.util.Set;
import java.util.UUID;

public class VGDPlayer {
    private UUID id;
    private VGDGroup vgdGroup;
    private Set<CuredVillager> curedVillagers;
}

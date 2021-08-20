package be.glenndecooman.villagergroupdiscount;

import be.glenndecooman.villagergroupdiscount.command.CommandManager;
import be.glenndecooman.villagergroupdiscount.listener.OnPlayerPreLogin;
import be.glenndecooman.villagergroupdiscount.listener.OnVillagerCured;
import be.glenndecooman.villagergroupdiscount.listener.OnVillagerDeath;
import be.glenndecooman.villagergroupdiscount.listener.OnVillagerInfected;
import be.glenndecooman.villagergroupdiscount.persistence.CuredVillagerDAOImpl;
import be.glenndecooman.villagergroupdiscount.persistence.JPAUtil;
import be.glenndecooman.villagergroupdiscount.persistence.VGDPlayerDAOImpl;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class VillagerGroupDiscount extends JavaPlugin {

    @Override
    public void onEnable() {
        JPAUtil.initialize(this);
        registerListeners();
        registerCommands();
        getLogger().info("Loaded successfully");
    }

    @Override
    public void onDisable() {
        JPAUtil.close();
    }

    private void registerListeners() {
        PluginManager pm = getServer().getPluginManager();



        pm.registerEvents(new OnPlayerPreLogin(), this);
        pm.registerEvents(new OnVillagerCured(this), this);
        pm.registerEvents(new OnVillagerDeath(), this);
        pm.registerEvents(new OnVillagerInfected(), this);
    }

    private void registerCommands() {
        PluginCommand vgd = this.getCommand("vgd");
        if (vgd != null) {
            vgd.setExecutor(new CommandManager());
        }
    }
}
